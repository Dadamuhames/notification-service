package com.uzumtech.notification.exception;


import lombok.Getter;

@Getter
public class EventPublisherNotFoundException extends RuntimeException {
    private final ErrorMessages errorMessage;

    public EventPublisherNotFoundException(ErrorMessages errorMessage) {
        this.errorMessage = errorMessage;
    }
}
