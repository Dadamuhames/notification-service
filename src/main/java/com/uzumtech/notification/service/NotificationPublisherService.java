package com.uzumtech.notification.service;

import com.uzumtech.notification.dto.event.NotificationEvent;
import com.uzumtech.notification.entity.enums.NotificationType;

public interface NotificationPublisherService {
    void publish(final NotificationEvent event);

    NotificationType getNotificationType();
}
