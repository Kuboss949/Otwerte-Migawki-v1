package pl.otwartemigawki.OtwarteMigawkiApp.service;

import pl.otwartemigawki.OtwarteMigawkiApp.model.AvailableDate;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserSession;

import java.time.LocalDate;

public interface UserSessionService {
    void  makeReservation(UserSession userSession);
}
