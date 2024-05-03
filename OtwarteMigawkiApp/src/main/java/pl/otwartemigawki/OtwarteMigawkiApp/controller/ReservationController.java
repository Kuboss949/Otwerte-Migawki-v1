package pl.otwartemigawki.OtwarteMigawkiApp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.ReservationRequestDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.SessionType;
import pl.otwartemigawki.OtwarteMigawkiApp.model.User;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserSession;
import pl.otwartemigawki.OtwarteMigawkiApp.service.SessionService;
import pl.otwartemigawki.OtwarteMigawkiApp.service.UserService;
import pl.otwartemigawki.OtwarteMigawkiApp.service.UserSessionService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    private final UserController userController;
    private final UserSessionService userSessionService;
    private final SessionService sessionService;
    private final UserService userService;

    public ReservationController(UserService userService, UserController userController, UserSessionService userSessionService, SessionService sessionService) {
        this.userController = userController;
        this.userSessionService = userSessionService;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> makeReservation(@RequestBody ReservationRequestDTO request) {
        User user;
        if (request.isRegistered()) {
            user = userService.getUserById(request.getId());
        } else {
            user = userController.createTemporaryUser(request.getTemporaryUserInfo());
        }
        LocalDateTime dateTime = LocalDateTime.of(request.getDate(), java.time.LocalTime.of(request.getHour(), 0));
        Instant instantDateTime = dateTime.toInstant(ZoneOffset.UTC);
        SessionType sessionType = sessionService.getSessionTypeByName(request.getSessionTypeName());
        UserSession userSession = new UserSession();
        userSession.setIdUser(user);
        userSession.setDate(instantDateTime);
        userSession.setIdSessionType(sessionType);

        userSessionService.makeReservation(userSession);

        return ResponseEntity.ok("Reservation made successfully.");
    }

}
