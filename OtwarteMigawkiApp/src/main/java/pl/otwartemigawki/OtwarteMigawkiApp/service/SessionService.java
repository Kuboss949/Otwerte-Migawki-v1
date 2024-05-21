package pl.otwartemigawki.OtwarteMigawkiApp.service;

import pl.otwartemigawki.OtwarteMigawkiApp.model.SessionType;
import pl.otwartemigawki.OtwarteMigawkiApp.model.UserSession;

import java.util.List;

public interface SessionService {
    List<SessionType> getAllSessionTypes();
    void addSessionType(SessionType sessionType);
    void deleteSessionType(Long sessionTypeId);

    SessionType getSessionTypeByName(String sessionTypeName);

    List<UserSession> getAllUpcomingSessions();
}
