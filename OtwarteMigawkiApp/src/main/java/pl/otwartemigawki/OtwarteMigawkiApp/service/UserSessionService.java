package pl.otwartemigawki.OtwarteMigawkiApp.service;

import pl.otwartemigawki.OtwarteMigawkiApp.dto.UpcomingSessionDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.User;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserSession;

import java.util.List;
import java.util.Optional;

public interface UserSessionService {
    void  makeReservation(UserSession userSession);
    List<UserSession> getAllSessionsForUser(User user);
    List<UpcomingSessionDTO> getAllUpcomingSessions();

    List<UpcomingSessionDTO> getAllSessionsWithoutGalleries();

    Optional<UserSession> getUserSessionById(Integer sessionId);

    List<UpcomingSessionDTO> getAllUpcomingSessionsForUserById(Integer id);
}
