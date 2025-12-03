package com.uzumtech.notification.service;

import com.uzumtech.notification.dto.event.WebhookEvent;

public interface WebhookService {
    void sendTerminalStatus(final WebhookEvent event, final String webhook);
}
