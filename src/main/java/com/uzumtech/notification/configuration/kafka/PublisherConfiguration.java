package com.uzumtech.notification.configuration.kafka;

import com.uzumtech.notification.constant.enums.NotificationType;
import com.uzumtech.notification.component.kafka.NotificationPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class PublisherConfiguration {

    @Bean(name = "eventPublishers")
    public Map<NotificationType, NotificationPublisher> eventPublishers(List<NotificationPublisher> publishers) {
        Map<NotificationType, NotificationPublisher> publishersMap = new HashMap<>();

        for (var publisher : publishers) {
            publishersMap.put(publisher.getNotificationType(), publisher);
        }

        return publishersMap;
    }
}
