package com.uzumtech.notification.handler;

import com.uzumtech.notification.dto.response.error.CommonErrorBody;
import com.uzumtech.notification.dto.response.error.ErrorResponse;
import com.uzumtech.notification.exception.EventPublisherNotFoundException;
import com.uzumtech.notification.exception.LoginNotFoundException;
import com.uzumtech.notification.exception.MerchantValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse<String>> handleAllExceptions(Exception ex) {
//        ErrorResponse<String> response = ErrorResponse.of(ex.getMessage());
//
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
