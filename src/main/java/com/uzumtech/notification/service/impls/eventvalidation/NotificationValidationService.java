package com.uzumtech.notification.service.impls.eventvalidation;

import com.uzumtech.notification.dto.event.NotificationEvent;
import com.uzumtech.notification.constant.enums.KafkaErrorMessage;
import com.uzumtech.notification.exception.kafka.nontransients.EventDtoInvalidException;
import com.uzumtech.notification.repository.NotificationRepository;
import com.uzumtech.notification.service.EventValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationValidationService implements EventValidationService<NotificationEvent> {
    private final NotificationRepository notificationRepository;

    @Override
    public void validate(NotificationEvent event) throws EventDtoInvalidException {
        boolean notificationIdValid = notificationRepository.existsByIdAndStatusNotSent(event.id());

        if (!notificationIdValid) {
            throw new EventDtoInvalidException(KafkaErrorMessage.NOTIFICATION_EVENT_MALFORMED);
        }
    }
}
