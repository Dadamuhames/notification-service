package com.uzumtech.notification.dto.request;

import com.uzumtech.notification.entity.enums.NotificationStatus;

public record WebhookRequest(Long notificationId, NotificationStatus status) {
}
