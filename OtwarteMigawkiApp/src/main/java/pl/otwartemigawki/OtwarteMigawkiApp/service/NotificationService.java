package pl.otwartemigawki.OtwarteMigawkiApp.service;

import java.util.List;

public interface NotificationService {
    void notifyUser(String username, String message);
    List<String> getNotifications(String username);
}
