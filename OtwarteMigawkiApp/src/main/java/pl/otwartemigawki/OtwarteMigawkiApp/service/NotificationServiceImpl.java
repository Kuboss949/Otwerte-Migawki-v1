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
    public String getNotification(String username) {
        String queueName = "notificationQueue-" + username;
        Queue queue = new Queue(queueName, false);
        amqpAdmin.declareQueue(queue);
        Object message = rabbitTemplate.receiveAndConvert(queueName);
        String notification = message == null ? "" : message.toString();

        return notification;
    }
}
