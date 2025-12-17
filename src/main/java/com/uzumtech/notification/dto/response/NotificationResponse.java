package com.uzumtech.notification.dto.response;

import com.uzumtech.notification.constant.enums.NotificationStatus;
import com.uzumtech.notification.constant.enums.NotificationType;

import java.time.OffsetDateTime;

public record NotificationResponse(Long id, String text, NotificationType type, NotificationStatus status,
                                   String receiverInfo, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
}
