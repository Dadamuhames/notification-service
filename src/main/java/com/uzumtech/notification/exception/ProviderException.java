package com.uzumtech.notification.exception;

import com.uzumtech.notification.constant.enums.Error;
import com.uzumtech.notification.constant.enums.ErrorType;
import org.springframework.http.HttpStatus;

public class ProviderException extends ApplicationException {
    public ProviderException(Error error) {
        super(error.getCode(), error.getMessage(), ErrorType.VALIDATION, HttpStatus.BAD_REQUEST);
    }
}
