package com.uzumtech.notification.dto.event;

import java.time.OffsetDateTime;

public record InvoiceEvent(String email, String name, Integer sentNotificationCount, OffsetDateTime date,
                           Long currentPrice) {}
