package pl.otwartemigawki.OtwarteMigawkiApp.service;

import org.springframework.stereotype.Service;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserSession;
import pl.otwartemigawki.OtwarteMigawkiApp.repository.UserSessionRepository;

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
}
