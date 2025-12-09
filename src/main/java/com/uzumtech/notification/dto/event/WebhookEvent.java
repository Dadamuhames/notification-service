package com.uzumtech.notification.dto.event;

import com.uzumtech.notification.constant.enums.WebhookRequestCodes;
import com.uzumtech.notification.constant.enums.NotificationStatus;
import jakarta.validation.constraints.NotNull;

public record WebhookEvent(@NotNull Long id, @NotNull Long merchantId, @NotNull NotificationStatus status,
                           @NotNull WebhookRequestCodes requestCode) {}
