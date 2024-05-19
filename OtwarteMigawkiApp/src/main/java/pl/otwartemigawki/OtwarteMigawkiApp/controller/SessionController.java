package pl.otwartemigawki.OtwarteMigawkiApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.*;
import pl.otwartemigawki.OtwarteMigawkiApp.model.*;
import pl.otwartemigawki.OtwarteMigawkiApp.service.*;
import pl.otwartemigawki.OtwarteMigawkiApp.util.SessionTypeDatesMapper;
import pl.otwartemigawki.OtwarteMigawkiApp.util.SessionUtil;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/session")
@CrossOrigin(origins = "http://localhost:3000")
public class SessionController {
    private final SessionService sessionService;
    private final TimeService timeService;
    private final AvailableDateService availableDateService;
    private final UserService userService;
    private final UserSessionService userSessionService;


    @Autowired
    public SessionController(SessionService sessionService, TimeService timeService, AvailableDateService availableDateService, UserService userService, UserSessionService userSessionService) {
        this.sessionService = sessionService;
        this.timeService = timeService;
        this.availableDateService = availableDateService;
        this.userService = userService;
        this.userSessionService = userSessionService;
    }

    @GetMapping("/all")

    public ResponseEntity<List<SessionTypeDTO>> getAllSessionTypes(){
        List<SessionType> types = sessionService.getAllSessionTypes();
        List<SessionTypeDTO> dtos = types.stream().map(SessionUtil::mapToDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponseDTO> addSession(@RequestBody SessionTypeDTO sessionTypeDTO){
        try {
            SessionType type = SessionUtil.createSessionTypeFromDTO(sessionTypeDTO);
            sessionService.addSessionType(type);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO("Session added successfully", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO("Failed to add session: " + e.getMessage(), false));
        }
    }

    @DeleteMapping("/delete/{sessionTypeId}")
    public ResponseEntity<ApiResponseDTO> deleteSessionType(@PathVariable Long sessionTypeId) {
        try{
            sessionService.deleteSessionType(sessionTypeId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO("Session deleted successfully", true));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO("Failed to delete session: " + e.getMessage(), false));
        }
    }

    @GetMapping("/fetchTimes")
    public ResponseEntity<List<SessionTypeDatesDTO>> getSessionTypesWithDates() {
        List<SessionType> sessionTypes = sessionService.getAllSessionTypes();
        List<SessionTypeDatesDTO> response = new ArrayList<>();

        for (SessionType sessionType : sessionTypes) {
            SessionTypeDatesDTO sessionTypeDatesDTO = SessionTypeDatesMapper.toDto(sessionType);
            response.add(sessionTypeDatesDTO);
        }

        return ResponseEntity.ok(response);
    }


    @PostMapping("/addTimes")
    public ResponseEntity<ApiResponseDTO> saveSessionTypesWithDates(@RequestBody SessionTypeDatesDTO sessionTypeDatesDTO) {
        String sessionTypeName = sessionTypeDatesDTO.getSessionTypeName();
        List<AvailableDateDTO> availableDates = sessionTypeDatesDTO.getAvailableDates();

        SessionType sessionType = sessionService.getSessionTypeByName(sessionTypeName);
        if (sessionType == null) {
            return ResponseEntity.badRequest().body(new ApiResponseDTO("Nie znaleziono typu sesji", false));
        }

        availableDates.forEach(availableDateDTO -> {
            List<String> times = availableDateDTO.getTimes();
            AvailableDate availableDate = availableDateService.createAvailableDate(sessionType, availableDateDTO);
            times.forEach(time -> timeService.saveTimeIfNotExists(availableDate, Integer.parseInt(time)));
        });

        return ResponseEntity.ok(new ApiResponseDTO("Data saved successfully", true));
    }

    @GetMapping("/sessions/{userId}")
    public ResponseEntity<List<UserSessionDTO>> getSessionsForUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        List<UserSessionDTO> galleries = userSessionService.getAllSessionsForUser(user).stream().map(SessionUtil::mapToDto).toList();
        return ResponseEntity.ok(galleries);
    }
}
