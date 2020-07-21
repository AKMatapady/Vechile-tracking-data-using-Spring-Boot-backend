package per.akmatapady.config;

import com.amazonaws.services.sns.AmazonSNS;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public NotificationMessagingTemplate getnotificationTemplate(AmazonSNS amazonSns)
    {
        return new NotificationMessagingTemplate(amazonSns);
    }
}
