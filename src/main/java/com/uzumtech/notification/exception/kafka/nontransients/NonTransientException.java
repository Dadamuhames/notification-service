package com.uzumtech.notification.exception.kafka.nontransients;

public class NonTransientException extends RuntimeException {
    public NonTransientException(String message) {
        super(message);
    }
}
