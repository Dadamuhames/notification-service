package com.uzumtech.notification.exception.kafka.nontransients;

import com.uzumtech.notification.exception.kafka.KafkaErrorMessage;

public class NonTransientException extends RuntimeException {
    public NonTransientException(String message) {
        super(message);
    }
}
