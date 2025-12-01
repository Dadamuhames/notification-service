package com.uzumtech.notification.service;

import com.uzumtech.notification.entity.enums.NotificationStatus;

public interface WebhookService {
    void sendTerminalStatus(final Long notificationId, final NotificationStatus status, final String webhook);
}
