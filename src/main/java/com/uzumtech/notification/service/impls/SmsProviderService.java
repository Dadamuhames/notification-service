package com.uzumtech.notification.service.impls;

import com.uzumtech.notification.dto.event.NotificationEvent;
import com.uzumtech.notification.service.ProviderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class SmsProviderService implements ProviderService {
    @Override
    public void send(NotificationEvent event) {
    }
}
