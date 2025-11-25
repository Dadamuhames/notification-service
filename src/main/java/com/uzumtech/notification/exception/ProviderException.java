package com.uzumtech.notification.exception;

public class ProviderException extends RuntimeException {
    public ProviderException(String message) {
        super(message);
    }

    public ProviderException(ErrorMessages message) {
        super(message.getMessage());
    }
}
