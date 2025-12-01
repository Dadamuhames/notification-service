package com.uzumtech.notification.exception;

import lombok.Getter;

@Getter
public enum ErrorMessages {
    USERNAME_NOT_FOUND("login invalid"),
    LOGIN_OR_TAX_NUMBER_NOT_UNIQUE("login or taxNumber already in use"),
    EVENT_PUBLISHER_NOT_IMPLEMENTED("Event publisher not implemented"),
    PRICE_NOT_FOUND("Price not found"),
    MERCHANT_ID_INVALID("Merchant id invalid");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }
}
