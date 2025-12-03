package com.uzumtech.notification.handler;

import com.uzumtech.notification.constant.ErrorMessages;
import com.uzumtech.notification.dto.response.error.CommonErrorBody;
import com.uzumtech.notification.dto.response.error.ErrorResponse;
import com.uzumtech.notification.exception.EventPublisherNotFoundException;
import com.uzumtech.notification.exception.LoginNotFoundException;
import com.uzumtech.notification.exception.MerchantValidationException;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginNotFoundException.class)
    public ResponseEntity<ErrorResponse<CommonErrorBody>> handleLoginNotFoundException(final LoginNotFoundException ex) {
        CommonErrorBody body = CommonErrorBody.of(ex.getErrorMessage());
        ErrorResponse<CommonErrorBody> response = ErrorResponse.of(body);

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MerchantValidationException.class)
    public ResponseEntity<ErrorResponse<CommonErrorBody>> handleMerchantValidationException(final MerchantValidationException ex) {
        CommonErrorBody body = CommonErrorBody.of(ex.getErrorMessage());
        ErrorResponse<CommonErrorBody> response = ErrorResponse.of(body);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EventPublisherNotFoundException.class)
    public ResponseEntity<ErrorResponse<CommonErrorBody>> handleEventPublisherNotFound(final EventPublisherNotFoundException ex) {
        CommonErrorBody body = CommonErrorBody.of(ex.getErrorMessage());
        ErrorResponse<CommonErrorBody> response = ErrorResponse.of(body);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse<CommonErrorBody>> handleMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex) {
        CommonErrorBody body = CommonErrorBody.of(ErrorMessages.MEDIA_TYPE_NOT_SUPPORTED, ex.getMessage());
        ErrorResponse<CommonErrorBody> response = ErrorResponse.of(body);

        return new ResponseEntity<>(response, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse<CommonErrorBody>> handleMissingRequestHeader(final MissingRequestHeaderException ex) {
        CommonErrorBody body = CommonErrorBody.of(ErrorMessages.MISSING_HEADER, ex.getMessage());
        ErrorResponse<CommonErrorBody> response = ErrorResponse.of(body);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse<CommonErrorBody>> handleHttpMethodNotSupported(final HttpRequestMethodNotSupportedException ex) {
        CommonErrorBody body = CommonErrorBody.of(ErrorMessages.METHOD_NOT_SUPPORTED, ex.getMessage());
        ErrorResponse<CommonErrorBody> response = ErrorResponse.of(body);

        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }


    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorResponse<CommonErrorBody>> handleResourceAccessEx(final ResourceAccessException ex) {
        CommonErrorBody body = CommonErrorBody.of(ErrorMessages.ACCESS_DENIED, ex.getMessage());
        ErrorResponse<CommonErrorBody> response = ErrorResponse.of(body);

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity<ErrorResponse<CommonErrorBody>> handleInterruptedException(InterruptedException ex) {
        CommonErrorBody body = CommonErrorBody.of(ErrorMessages.THREAD_INTERRUPTED, ex.getMessage());
        ErrorResponse<CommonErrorBody> response = ErrorResponse.of(body);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
