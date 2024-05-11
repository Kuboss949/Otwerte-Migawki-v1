package pl.otwartemigawki.OtwarteMigawkiApp.service;

import pl.otwartemigawki.OtwarteMigawkiApp.model.AvailableDate;
import pl.otwartemigawki.OtwarteMigawkiApp.model.User;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserSession;

import java.time.LocalDate;
import java.util.List;

public interface UserSessionService {
    void  makeReservation(UserSession userSession);
    List<UserSession> getAllSessionsForUser(User user);
}
