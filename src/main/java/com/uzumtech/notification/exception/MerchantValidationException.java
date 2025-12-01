package com.uzumtech.notification.exception;

import lombok.Getter;

@Getter
public class MerchantValidationException extends RuntimeException {
    private final ErrorMessages errorMessage;

    public MerchantValidationException(String message, ErrorMessages errorMessage) {
        super(message);
        this.errorMessage = errorMessage;
    }

    public MerchantValidationException(ErrorMessages errorMessage) {
        this.errorMessage = errorMessage;
    }
}
