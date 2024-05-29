package pl.otwartemigawki.OtwarteMigawkiApp.service;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{

    private final RabbitTemplate rabbitTemplate;
    private final AmqpAdmin amqpAdmin;

    @Autowired
    public NotificationServiceImpl(RabbitTemplate rabbitTemplate, AmqpAdmin amqpAdmin) {
        this.rabbitTemplate = rabbitTemplate;
        this.amqpAdmin = amqpAdmin;
    }

    @Override
    public void notifyUser(String username, String message) {
        String queueName = "notificationQueue-" + username;

        Queue queue = new Queue(queueName, false);
        amqpAdmin.declareQueue(queue);

        rabbitTemplate.convertAndSend(queueName, message);
    }

    @Override
    public List<String> getNotifications(String username) {
        String queueName = "notificationQueue-" + username;
        Queue queue = new Queue(queueName, false);
        amqpAdmin.declareQueue(queue);
        List<String> notifications = new ArrayList<>();
        boolean hasMessages = true;

        while (hasMessages) {
            Object message = rabbitTemplate.receiveAndConvert(queueName);
            if (message != null) {
                notifications.add(message.toString());
            } else {
                hasMessages = false;
            }
        }

        return notifications;
    }
}
