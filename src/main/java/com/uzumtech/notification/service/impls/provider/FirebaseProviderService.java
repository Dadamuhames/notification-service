package com.uzumtech.notification.service.impls.provider;

import com.uzumtech.notification.dto.event.NotificationEvent;
import com.uzumtech.notification.service.ProviderService;
import org.springframework.stereotype.Service;


@Service
public class FirebaseProviderService implements ProviderService<NotificationEvent> {
    @Override
    public void send(NotificationEvent event) {

    }
}
