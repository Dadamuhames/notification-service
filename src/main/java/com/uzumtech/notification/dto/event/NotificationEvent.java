package com.uzumtech.notification.dto.event;

import com.uzumtech.notification.entity.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotificationEvent(@NotNull Long id, @NotNull Long merchantId,
                                @NotBlank(message = "merchantName required") String merchantName,
                                @NotBlank(message = "text required") String text,
                                @NotBlank(message = "receiverInfo required") String receiverInfo,
                                @NotNull NotificationType type) {}
