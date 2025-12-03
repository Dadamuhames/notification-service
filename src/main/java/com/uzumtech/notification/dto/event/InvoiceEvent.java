package com.uzumtech.notification.dto.event;

import com.uzumtech.notification.validation.EmailConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record InvoiceEvent(@NotNull(message = "Email required") @EmailConstraint String email,
                           @NotBlank(message = "name required") String name,
                           @NotNull(message = "notification count required") @Min(0) Integer sentNotificationCount,
                           @NotNull(message = "date required") OffsetDateTime date,
                           @NotNull(message = "current price required") Long currentPrice) {}
