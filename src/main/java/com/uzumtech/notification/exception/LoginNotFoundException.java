package com.uzumtech.notification.exception;


import com.uzumtech.notification.constant.ErrorMessages;
import lombok.Getter;

@Getter
public class LoginNotFoundException extends RuntimeException {
    public ErrorMessages errorMessage;

    public LoginNotFoundException(ErrorMessages errorMessage) {
        this.errorMessage = errorMessage;
    }

}
