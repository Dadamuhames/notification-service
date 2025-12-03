package com.uzumtech.notification.exception;

import com.uzumtech.notification.constant.ErrorMessages;

public class ProviderException extends RuntimeException {
    public ProviderException(String message) {
        super(message);
    }

    public ProviderException(ErrorMessages message) {
        super(message.getMessage());
    }
}
