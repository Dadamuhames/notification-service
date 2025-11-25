package com.uzumtech.notification.exception;


import lombok.Getter;

@Getter
public class LoginNotFoundException extends RuntimeException {
    public ErrorMessages errorMessage;

    public LoginNotFoundException(ErrorMessages errorMessage) {
        this.errorMessage = errorMessage;
    }

}
