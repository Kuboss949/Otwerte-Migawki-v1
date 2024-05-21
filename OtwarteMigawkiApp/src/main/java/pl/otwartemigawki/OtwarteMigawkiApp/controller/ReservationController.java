package pl.otwartemigawki.OtwarteMigawkiApp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.ApiResponseDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.ReservationRequestDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.*;
import pl.otwartemigawki.OtwarteMigawkiApp.service.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    private final UserController userController;
    private final UserSessionService userSessionService;
    private final SessionService sessionService;
    private final UserService userService;
    private final JwtService jwtService;
    private final AvailableDateService availableDateService;
    private final TimeService timeService;

    public ReservationController(UserService userService, UserController userController, UserSessionService userSessionService, SessionService sessionService, JwtService jwtService, AvailableDateService availableDateService, TimeService timeService) {
        this.userController = userController;
        this.userSessionService = userSessionService;
        this.userService = userService;
        this.sessionService = sessionService;
        this.jwtService = jwtService;
        this.availableDateService = availableDateService;
        this.timeService = timeService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponseDTO> makeReservation(@CookieValue(name = "jwtToken", defaultValue = "defaultValue") String cookieValue, @RequestBody ReservationRequestDTO request) {
        try{
            User user;
            if (Objects.equals(request.getIsRegistered(), "true")) {
                user = userService.getUserByEmail(jwtService.extractUserName(cookieValue));
            } else {
                user = userController.createTemporaryUser(request.getTemporaryUserInfo());
            }
            AvailableDate date = availableDateService.getAvailableDateByDateAndTime(request.getDate(), request.getHour());
            if(date != null){
                LocalDateTime dateTime = LocalDateTime.of(request.getDate(), java.time.LocalTime.of(request.getHour(), 0));
                Instant instantDateTime = dateTime.toInstant(ZoneOffset.UTC);
                SessionType sessionType = sessionService.getSessionTypeByName(request.getSessionTypeName());
                UserSession userSession = new UserSession();
                userSession.setIdUser(user);
                userSession.setDate(instantDateTime);
                userSession.setIdSessionType(sessionType);
                userSessionService.makeReservation(userSession);
                Optional<Time> timeToDelete = date.getTimes().stream()
                        .filter(time -> Objects.equals(time.getHour(), request.getHour()))
                        .findFirst();
                timeService.deleteTime(timeToDelete.get());
                if(date.getTimes().size() == 1)
                    availableDateService.deleteAvailableDate(date);
                return ResponseEntity.ok( new ApiResponseDTO("Rezerwascja zakończona powodzeniem!",true));
            }
        }catch (Exception e){
            return ResponseEntity.ok( new ApiResponseDTO("Wystąpiły problemy z utworzeniem rezerwacji, spróbuj ponownie później.",false));
        }
        return ResponseEntity.ok( new ApiResponseDTO("Wystąpiły problemy z utworzeniem rezerwacji, spróbuj ponownie później.",false));
    }

}
