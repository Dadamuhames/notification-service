package com.uzumtech.notification.component.kafka.publisher.notification;

import com.uzumtech.notification.dto.event.NotificationEvent;
import com.uzumtech.notification.constant.enums.NotificationType;
import com.uzumtech.notification.constant.enums.Error;
import com.uzumtech.notification.exception.EventPublisherNotFoundException;
import com.uzumtech.notification.component.kafka.KafkaEventPublisher;
import com.uzumtech.notification.component.kafka.NotificationPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KafkaNotificationPublisher implements KafkaEventPublisher<NotificationEvent> {
    private final Map<NotificationType, NotificationPublisher> eventPublishers;

    @Override
    public void publish(final NotificationEvent event) {
        NotificationPublisher publisher = eventPublishers.get(event.type());

        if (publisher == null) {
            throw new EventPublisherNotFoundException(Error.EVENT_PUBLISHER_NOT_IMPLEMENTED);
        }

        publisher.publish(event);
    }
}
