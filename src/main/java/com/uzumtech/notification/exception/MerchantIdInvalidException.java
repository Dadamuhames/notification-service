package com.uzumtech.notification.exception;

import lombok.Getter;

@Getter
public class MerchantIdInvalidException extends RuntimeException {
    private final ErrorMessages errorMessage;

    public MerchantIdInvalidException(String message, ErrorMessages errorMessage) {
        super(message);
        this.errorMessage = errorMessage;
    }

    public MerchantIdInvalidException(ErrorMessages errorMessage) {
        this.errorMessage = errorMessage;
    }
}
