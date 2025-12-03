package com.uzumtech.notification.constant;

import lombok.Getter;

@Getter
public enum ErrorMessages {
    USERNAME_NOT_FOUND("login invalid"),
    LOGIN_OR_TAX_NUMBER_NOT_UNIQUE("login or taxNumber already in use"),
    EVENT_PUBLISHER_NOT_IMPLEMENTED("Event publisher not implemented"),
    PRICE_NOT_FOUND("Price not found"),
    MEDIA_TYPE_NOT_SUPPORTED("Media type not supported"),
    MISSING_HEADER("Missing header exception"),
    METHOD_NOT_SUPPORTED("Http method not supported"),
    ACCESS_DENIED("Access denied"),
    THREAD_INTERRUPTED("Thread interrupted");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }
}
