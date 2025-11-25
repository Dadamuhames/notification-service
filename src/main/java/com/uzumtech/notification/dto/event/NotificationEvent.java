package com.uzumtech.notification.dto.event;

import com.uzumtech.notification.entity.enums.NotificationType;

public record NotificationEvent(Long id, Long merchantId, String text, String receiverInfo, NotificationType type) {}
