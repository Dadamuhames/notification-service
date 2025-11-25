package com.uzumtech.notification.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class MerchantValidationException extends RuntimeException {
    private final Map<String, ErrorMessages> messages;

    public MerchantValidationException(String message, Map<String, ErrorMessages> messages) {
        super(message);
        this.messages = messages;
    }

    public MerchantValidationException(Map<String, ErrorMessages> messages) {
        this.messages = messages;
    }
}
