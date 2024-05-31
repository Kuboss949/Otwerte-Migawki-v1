package pl.otwartemigawki.OtwarteMigawkiApp.service;

import org.springframework.stereotype.Service;
import pl.otwartemigawki.OtwarteMigawkiApp.dto.UpcomingSessionDTO;
import pl.otwartemigawki.OtwarteMigawkiApp.model.User;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserSession;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.UserSessionRepository;
import pl.otwartemigawki.OtwarteMigawkiApp.util.SessionMapper;

import java.util.List;
import java.util.Optional;

@Service
public class UserSessionServiceImpl implements UserSessionService{

    private final UserSessionRepository userSessionRepository;

    public UserSessionServiceImpl(UserSessionRepository userSessionRepository) {
        this.userSessionRepository = userSessionRepository;
    }

    @Override
    public void makeReservation(UserSession userSession) {
        userSessionRepository.save(userSession);
    }

    @Override
    public List<UserSession> getAllSessionsForUser(User user) {
        return userSessionRepository.findUserSessionsByIdUser(user);
    }

    @Override
    public List<UpcomingSessionDTO> getAllUpcomingSessions() {
        return userSessionRepository.findAllUpcomingSessions().stream()
                .map(SessionMapper::mapUserSessionToUpcomingSessionDTO).toList();
    }

    @Override
    public List<UpcomingSessionDTO> getAllSessionsWithoutGalleries() {
        return userSessionRepository.findAllSessionsWithoutGalleries().stream()
                .map(SessionMapper::mapUserSessionToUpcomingSessionDTO).toList();
    }

    @Override
    public Optional<UserSession> getUserSessionById(Integer sessionId) {
        return userSessionRepository.findById(sessionId);
    }

    @Override
    public List<UpcomingSessionDTO> getAllUpcomingSessionsForUserById(Integer id) {
        return userSessionRepository.findAllUpcomingSessionsForUserById(id).stream()
                .map(SessionMapper::mapUserSessionToUpcomingSessionDTO).toList();
    }

    @Override
    public void deleteSessionByID(Integer id) {
        userSessionRepository.deleteById(id);
    }
}
