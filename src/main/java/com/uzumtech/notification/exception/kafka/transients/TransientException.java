package com.uzumtech.notification.exception.kafka.transients;

import com.uzumtech.notification.exception.kafka.KafkaErrorMessage;
import lombok.Getter;

@Getter
public class TransientException extends RuntimeException {
    private final KafkaErrorMessage errorMessage;

    public TransientException(String message, KafkaErrorMessage errorMessage) {
        super(message);
        this.errorMessage = errorMessage;
    }

    public TransientException(KafkaErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
