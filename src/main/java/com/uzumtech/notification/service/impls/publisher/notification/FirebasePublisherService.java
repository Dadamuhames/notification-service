package com.uzumtech.notification.service.impls.publisher.notification;

import com.uzumtech.notification.dto.event.NotificationEvent;
import com.uzumtech.notification.constant.enums.NotificationType;
import com.uzumtech.notification.service.NotificationPublisherService;
import com.uzumtech.notification.constant.KafkaConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirebasePublisherService implements NotificationPublisherService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publish(final NotificationEvent event) {
        kafkaTemplate.send(KafkaConstants.PUSH_TOPIC, event);
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.PUSH;
    }
}
