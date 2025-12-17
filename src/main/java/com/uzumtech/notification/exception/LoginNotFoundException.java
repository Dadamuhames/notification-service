package com.uzumtech.notification.exception;


import com.uzumtech.notification.constant.enums.Error;
import com.uzumtech.notification.constant.enums.ErrorType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LoginNotFoundException extends ApplicationException {
    public LoginNotFoundException(Error error) {
        super(error.getCode(), error.getMessage(), ErrorType.VALIDATION, HttpStatus.BAD_REQUEST);
    }
}
