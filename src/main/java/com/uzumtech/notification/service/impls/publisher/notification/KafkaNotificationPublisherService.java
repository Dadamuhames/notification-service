package com.uzumtech.notification.service.impls.publisher.notification;

import com.uzumtech.notification.dto.event.NotificationEvent;
import com.uzumtech.notification.constant.enums.NotificationType;
import com.uzumtech.notification.constant.enums.Error;
import com.uzumtech.notification.exception.EventPublisherNotFoundException;
import com.uzumtech.notification.service.KafkaEventPublisherService;
import com.uzumtech.notification.service.NotificationPublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KafkaNotificationPublisherService implements KafkaEventPublisherService<NotificationEvent> {
    private final Map<NotificationType, NotificationPublisherService> eventPublishers;

    @Override
    public void publish(final NotificationEvent event) {
        NotificationPublisherService publisher = eventPublishers.get(event.type());

        if (publisher == null) {
            throw new EventPublisherNotFoundException(Error.EVENT_PUBLISHER_NOT_IMPLEMENTED);
        }

        publisher.publish(event);
    }
}
