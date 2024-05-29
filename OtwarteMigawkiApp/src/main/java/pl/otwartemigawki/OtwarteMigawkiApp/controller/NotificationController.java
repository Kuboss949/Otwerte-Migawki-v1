package pl.otwartemigawki.OtwarteMigawkiApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.otwartemigawki.OtwarteMigawkiApp.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/{username}")
    public ResponseEntity<List<String>> getNotifications(@PathVariable String username) {
        List<String> notifications = notificationService.getNotifications(username);
        return ResponseEntity.ok().body(notifications);
    }
}
