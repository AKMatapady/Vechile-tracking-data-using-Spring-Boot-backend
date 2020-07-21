package per.akmatapady.awsmessaging;

import com.amazonaws.services.sns.AmazonSNS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class AlertNotification {


    private final NotificationMessagingTemplate notificationMessagingTemplate;

    @Value("${vehicle.alert.topic}")
    private String topic;

    @Autowired
    public AlertNotification(NotificationMessagingTemplate amazonSnsNotificationTemplate) {
        this.notificationMessagingTemplate = amazonSnsNotificationTemplate;
    }

    public void send(String subject, String message) {
        this.notificationMessagingTemplate.sendNotification(topic, message, subject);
    }
}
