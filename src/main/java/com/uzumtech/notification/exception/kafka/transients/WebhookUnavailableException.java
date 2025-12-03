package com.uzumtech.notification.exception.kafka.transients;

import com.uzumtech.notification.constant.KafkaErrorMessage;

public class WebhookUnavailableException extends TransientException {
    public WebhookUnavailableException(String message) {
        super(message);
    }


    public WebhookUnavailableException(KafkaErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
