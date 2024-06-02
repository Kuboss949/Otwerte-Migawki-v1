package pl.otwartemigawki.OtwarteMigawkiApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.*;
import pl.otwartemigawki.OtwarteMigawkiApp.model.*;
import pl.otwartemigawki.OtwarteMigawkiApp.service.*;
import pl.otwartemigawki.OtwarteMigawkiApp.util.SessionTypeDatesMapper;
import pl.otwartemigawki.OtwarteMigawkiApp.util.SessionMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/session")
public class SessionController {
    private final SessionTypeService sessionTypeService;
    private final TimeService timeService;
    private final AvailableDateService availableDateService;
    private final UserService userService;
    private final UserSessionService userSessionService;
    private final JwtService jwtService;


    @Autowired
    public SessionController(SessionTypeService sessionTypeService, TimeService timeService, AvailableDateService availableDateService, UserService userService, UserSessionService userSessionService, JwtService jwtService) {
        this.sessionTypeService = sessionTypeService;
        this.timeService = timeService;
        this.availableDateService = availableDateService;
        this.userService = userService;
        this.userSessionService = userSessionService;
        this.jwtService = jwtService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<SessionTypeDTO>> getAllSessionTypes(){
        List<SessionTypeDTO> result = sessionTypeService.getAllSessionTypes();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all-enabled")
    public ResponseEntity<List<SessionTypeDTO>> getAllEnabledSessionTypes(){
        List<SessionTypeDTO> result = sessionTypeService.getAllEnabledSessionTypes();
        return ResponseEntity.ok(result);
    }

    @PostMapping(headers = {"Content-Type=multipart/form-data"}, value = "/add")
    public ResponseEntity<ApiResponseDTO> addSession(@ModelAttribute AddSessionTypeRequestDTO request){
        try {
            sessionTypeService.addSessionType(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO("Pomyślnie dodano nowy typ sesji!", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO("Błąd: nie udało się dodać sesji", false));
        }
    }

    @DeleteMapping("/delete/{sessionTypeId}")
    public ResponseEntity<ApiResponseDTO> deleteSessionType(@PathVariable Long sessionTypeId) {
        try{
            sessionTypeService.deleteSessionType(sessionTypeId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO("Session deleted successfully", true));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO("Failed to delete session: " + e.getMessage(), false));
        }
    }

    @GetMapping("/fetchTimes")
    public ResponseEntity<List<SessionTypeDatesDTO>> getSessionTypesWithDates() {
        List<SessionTypeDatesDTO> response = sessionTypeService.getAllSessionTypesWithDates();
        return ResponseEntity.ok(response);
    }


    @PostMapping("/addTimes")
    public ResponseEntity<ApiResponseDTO> saveSessionTypesWithDates(@RequestBody SessionTypeDatesDTO sessionTypeDatesDTO) {
        String sessionTypeName = sessionTypeDatesDTO.getSessionTypeName();
        List<AvailableDateDTO> availableDates = sessionTypeDatesDTO.getAvailableDates();

        SessionType sessionType = sessionTypeService.getSessionTypeByName(sessionTypeName);
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
        List<UserSessionDTO> galleries = userSessionService.getAllSessionsForUser(user);
        return ResponseEntity.ok(galleries);
    }

    @GetMapping("/all-upcoming")
    public ResponseEntity<List<UpcomingSessionDTO>> getAllUpcomingSessions()
    {
        List<UpcomingSessionDTO> upcomingSessions = userSessionService.getAllUpcomingSessions();
        return ResponseEntity.ok(upcomingSessions);
    }

    @GetMapping("/all-user-upcoming")
    public ResponseEntity<List<UpcomingSessionDTO>> getAllUpcomingSessionsForUser(@CookieValue(name = "jwtToken", defaultValue = "defaultValue") String cookieValue)
    {
        try{
            User user = userService.getUserByEmail(jwtService.extractUserName(cookieValue));
            List<UpcomingSessionDTO> response = userSessionService.getAllUpcomingSessionsForUserById(user.getId());
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @GetMapping("/all-without-galleries")
    public ResponseEntity<List<UpcomingSessionDTO>> getAllSessionsWithoutGalleries()
    {

        List<UpcomingSessionDTO> sessions = userSessionService.getAllSessionsWithoutGalleries();
        return ResponseEntity.ok(sessions);
    }

    @PostMapping("/change-disable-session-state/{id}")
    public ResponseEntity<ApiResponseDTO> changeSessionTypeToDisabled(@PathVariable Integer id){
        try{
            sessionTypeService.toggleDisableSessionState(id);
            return ResponseEntity.ok(new ApiResponseDTO("Pomyślnie zmieniono status sesji!", true));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO("Problem ze zmianą stanu sesji.", false));
        }
    }

    @DeleteMapping("/cancel-session/{id}")
    public ResponseEntity<ApiResponseDTO> cancelSession(@PathVariable Integer id){
        try{
            userSessionService.deleteSessionByID(id);
            return ResponseEntity.ok(new ApiResponseDTO("Pomyślnie odwołano sesję!", true));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO("Problem z odwołaniem sesji, spróbuj ponownie później.", false));
        }
    }
}
