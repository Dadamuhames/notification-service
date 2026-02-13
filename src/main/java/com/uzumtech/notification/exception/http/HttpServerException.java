package com.uzumtech.notification.exception.http;

import com.uzumtech.notification.constant.enums.Error;
import com.uzumtech.notification.constant.enums.ErrorType;
import com.uzumtech.notification.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class HttpServerException extends ApplicationException {

    public HttpServerException(String message, HttpStatusCode status) {
        super(Error.HTTP_SERVICE_ERROR_CODE.getCode(), message, ErrorType.EXTERNAL, HttpStatus.valueOf(status.value()));
    }
}
