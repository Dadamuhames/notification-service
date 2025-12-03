package com.uzumtech.notification.dto.response;

import lombok.Builder;

@Builder
public record NotificationSendResponse(Long notificationId) {
}
