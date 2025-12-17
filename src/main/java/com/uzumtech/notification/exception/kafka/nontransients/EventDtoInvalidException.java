package com.uzumtech.notification.exception.kafka.nontransients;

import com.uzumtech.notification.constant.enums.KafkaErrorMessage;

public class EventDtoInvalidException extends NonTransientException {
    public EventDtoInvalidException(String message) {
        super(message);
    }

    public EventDtoInvalidException(KafkaErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
