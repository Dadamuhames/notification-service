package com.uzumtech.notification.component.kafka;

import com.uzumtech.notification.dto.event.NotificationEvent;
import com.uzumtech.notification.constant.enums.NotificationType;

public interface NotificationPublisher {
    void publish(final NotificationEvent event);

    NotificationType getNotificationType();
}
