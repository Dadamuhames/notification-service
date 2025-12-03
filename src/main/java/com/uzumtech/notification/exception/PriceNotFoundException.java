package com.uzumtech.notification.exception;


import com.uzumtech.notification.constant.ErrorMessages;
import lombok.Getter;

@Getter
public class PriceNotFoundException extends RuntimeException {
    private final ErrorMessages errorMessage;

    public PriceNotFoundException(ErrorMessages errorMessage, String message) {
        super(message);
        this.errorMessage = errorMessage;
    }

    public PriceNotFoundException(ErrorMessages errorMessage) {
        this.errorMessage = errorMessage;
    }
}
