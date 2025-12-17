package com.uzumtech.notification.exception.kafka.nontransients;

import com.uzumtech.notification.constant.enums.KafkaErrorMessage;

public class MerchantIdInvalidException extends NonTransientException {
    public MerchantIdInvalidException(String message) {
        super(message);
    }

    public MerchantIdInvalidException(KafkaErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
