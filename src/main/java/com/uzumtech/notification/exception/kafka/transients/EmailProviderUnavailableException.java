package com.uzumtech.notification.exception.kafka.transients;

public class EmailProviderUnavailableException extends TransientException {
    public EmailProviderUnavailableException(Exception e) {
        super(e.getMessage());
    }
}
