package com.uzumtech.notification.exception;

import lombok.Getter;

@Getter
public enum ErrorMessages {
    USERNAME_NOT_FOUND("login invalid"),
    LOGIN_NOT_UNIQUE("login already in use"),
    TAX_NUMBER_NOT_UNIQUE("taxNumber already in use");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }
}
