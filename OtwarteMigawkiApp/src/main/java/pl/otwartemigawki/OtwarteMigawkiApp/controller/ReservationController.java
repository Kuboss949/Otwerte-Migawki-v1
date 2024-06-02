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
    private final SessionTypeService sessionTypeService;
    private final UserService userService;
    private final JwtService jwtService;
    private final AvailableDateService availableDateService;
    private final TimeService timeService;

    public ReservationController(UserService userService, UserController userController, UserSessionService userSessionService, SessionTypeService sessionTypeService, JwtService jwtService, AvailableDateService availableDateService, TimeService timeService) {
        this.userController = userController;
        this.userSessionService = userSessionService;
        this.userService = userService;
        this.sessionTypeService = sessionTypeService;
        this.jwtService = jwtService;
        this.availableDateService = availableDateService;
        this.timeService = timeService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponseDTO> makeReservation(@CookieValue(name = "jwtToken", defaultValue = "defaultValue") String cookieValue, @RequestBody ReservationRequestDTO request) {
        try {
            User user = getUser(cookieValue, request);
            SessionType sessionType = sessionTypeService.getSessionTypeByName(request.getSessionTypeName());
            AvailableDate date = sessionType.getAvailableDateByDateTime(request.getDate(), request.getHour());

            if (date == null) {
                return ResponseEntity.ok(new ApiResponseDTO("Wybrana data i godzina nie są dłużej dostępne.", false));
            }

            createReservation(user, request, date);
            return ResponseEntity.ok(new ApiResponseDTO("Rezerwacja zakończona sukcesem!", true));

        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponseDTO("Wystąpił problem z rezerwacją, prosimy spróbować później.", false));
        }
    }

    private User getUser(String cookieValue, ReservationRequestDTO request) {
        if (Objects.equals(request.getIsRegistered(), "true")) {
            return userService.getUserByEmail(jwtService.extractUserName(cookieValue));
        } else {
            return userService.createTemporaryUser(request.getTemporaryUserInfo());
        }
    }

    private void createReservation(User user, ReservationRequestDTO request, AvailableDate date) {
        LocalDateTime dateTime = LocalDateTime.of(request.getDate(), java.time.LocalTime.of(request.getHour(), 0));
        Instant instantDateTime = dateTime.toInstant(ZoneOffset.UTC);
        SessionType sessionType = sessionTypeService.getSessionTypeByName(request.getSessionTypeName());

        UserSession userSession = new UserSession();
        userSession.setIdUser(user);
        userSession.setDate(instantDateTime);
        userSession.setIdSessionType(sessionType);

        userSessionService.makeReservation(userSession);

        Optional<Time> timeToDelete = date.getTimes().stream()
                .filter(time -> Objects.equals(time.getHour(), request.getHour()))
                .findFirst();

        timeToDelete.ifPresent(timeService::deleteTime);

        if (date.getTimes().size() == 1) {
            availableDateService.deleteAvailableDate(date);
        }
    }
}
