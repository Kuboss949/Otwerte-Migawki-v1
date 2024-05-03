package pl.otwartemigawki.OtwarteMigawkiApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.*;
import pl.otwartemigawki.OtwarteMigawkiApp.model.*;
import pl.otwartemigawki.OtwarteMigawkiApp.service.AvailableDateService;
import pl.otwartemigawki.OtwarteMigawkiApp.service.SessionService;
import pl.otwartemigawki.OtwarteMigawkiApp.service.TimeService;
import pl.otwartemigawki.OtwarteMigawkiApp.util.SessionTypeDatesMapper;
import pl.otwartemigawki.OtwarteMigawkiApp.util.SessionUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/session")
public class SessionController {
    private final SessionService sessionService;
    private final TimeService timeService;
    private final AvailableDateService availableDateService;
    @Autowired
    public SessionController(SessionService sessionService, TimeService timeService, AvailableDateService availableDateService) {
        this.sessionService = sessionService;
        this.timeService = timeService;
        this.availableDateService = availableDateService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<SessionTypeDTO>> getAllSessionTypes(){
        List<SessionType> types = sessionService.getAllSessionTypes();
        List<SessionTypeDTO> dtos = types.stream().map(SessionUtil::convertToDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponseDTO> addSession(@RequestBody SessionTypeDTO sessionTypeDTO){
        try {
            SessionType type = SessionUtil.createSessionTypeFromDTO(sessionTypeDTO);
            sessionService.addSessionType(type);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO("Session added successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO("Failed to add session: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{sessionTypeId}")
    public ResponseEntity<ApiResponseDTO> deleteSessionType(@PathVariable Long sessionTypeId) {
        try{
            sessionService.deleteSessionType(sessionTypeId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO("Session deleted successfully"));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO("Failed to delete session: " + e.getMessage()));
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
    public ResponseEntity<String> saveSessionTypesWithDates(@RequestBody SessionTypeDatesDTO sessionTypeDatesDTO) {
        String sessionTypeName = sessionTypeDatesDTO.getSessionTypeName();
        List<AvailableDateDTO> availableDates = sessionTypeDatesDTO.getAvailableDates();

        SessionType sessionType = sessionService.getSessionTypeByName(sessionTypeName);
        if (sessionType == null) {
            return ResponseEntity.badRequest().body("Session type not found");
        }

        availableDates.forEach(availableDateDTO -> {
            AvailableDate availableDate = availableDateService.getOrCreateAvailableDate(sessionType, availableDateDTO);
            List<String> times = availableDateDTO.getTimes();
            times.forEach(time -> timeService.saveTimeIfNotExists(availableDate, Integer.parseInt(time)));
        });

        return ResponseEntity.ok("Data saved successfully");
    }
}
