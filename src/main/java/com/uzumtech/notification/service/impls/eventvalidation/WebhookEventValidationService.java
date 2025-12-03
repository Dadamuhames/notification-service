package com.uzumtech.notification.service.impls.eventvalidation;

import com.uzumtech.notification.constant.KafkaErrorMessage;
import com.uzumtech.notification.dto.event.WebhookEvent;
import com.uzumtech.notification.entity.enums.NotificationStatus;
import com.uzumtech.notification.exception.kafka.nontransients.EventDtoInvalidException;
import com.uzumtech.notification.exception.kafka.nontransients.NonTransientException;
import com.uzumtech.notification.service.EventValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebhookEventValidationService implements EventValidationService<WebhookEvent> {
    @Override
    public void validate(WebhookEvent event) throws NonTransientException {
        if (event.status().equals(NotificationStatus.CREATED)) {
            throw new EventDtoInvalidException(KafkaErrorMessage.WEBHOOK_EVENT_MALFORMED);
        }
    }
}
