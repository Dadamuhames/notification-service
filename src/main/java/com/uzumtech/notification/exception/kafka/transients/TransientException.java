package com.uzumtech.notification.exception.kafka.transients;

public class TransientException extends RuntimeException {

    public TransientException(String message) {
        super(message);
    }
}
