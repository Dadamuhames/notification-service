package com.uzumtech.notification.service.impls;

import com.uzumtech.notification.dto.event.NotificationEvent;
import com.uzumtech.notification.service.ProviderService;
import org.springframework.stereotype.Service;

@Service
public class EmailProviderService implements ProviderService {
    @Override
    public void send(NotificationEvent event) {

    }
}
