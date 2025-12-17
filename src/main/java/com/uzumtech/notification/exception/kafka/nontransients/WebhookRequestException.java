package com.uzumtech.notification.exception.kafka.nontransients;

import com.uzumtech.notification.constant.enums.KafkaErrorMessage;

public class WebhookRequestException extends NonTransientException {
    public WebhookRequestException(String message) {
        super(message);
    }

    public WebhookRequestException(KafkaErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
