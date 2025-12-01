package com.uzumtech.notification.exception.kafka.nontransients;

import com.uzumtech.notification.exception.kafka.KafkaErrorMessage;

public class NotificationInvalidException extends NonTransientException {
    public NotificationInvalidException(String message) {
        super(message);
    }

    public NotificationInvalidException(KafkaErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
