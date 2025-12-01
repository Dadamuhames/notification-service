package com.uzumtech.notification.exception.kafka;

import lombok.Getter;

@Getter
public enum KafkaErrorMessage {
    ;

    private final String message;

    KafkaErrorMessage(String message) {
        this.message = message;
    }
}
