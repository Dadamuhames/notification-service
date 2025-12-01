package com.uzumtech.notification.configuration.kafka;

import com.uzumtech.notification.entity.enums.NotificationType;
import com.uzumtech.notification.service.NotificationPublisherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class PublisherConfig {

    @Bean(name = "eventPublishers")
    public Map<NotificationType, NotificationPublisherService> eventPublishers(List<NotificationPublisherService> publishers) {
        Map<NotificationType, NotificationPublisherService> publishersMap = new HashMap<>();

        for (var publisher : publishers) {
            publishersMap.put(publisher.getNotificationType(), publisher);
        }

        return publishersMap;
    }
}
