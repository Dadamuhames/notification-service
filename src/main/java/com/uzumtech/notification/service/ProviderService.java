package com.uzumtech.notification.service;

import com.uzumtech.notification.dto.event.NotificationEvent;
import com.uzumtech.notification.exception.ProviderException;

public interface ProviderService {
    void send(final NotificationEvent event) throws ProviderException;
}
