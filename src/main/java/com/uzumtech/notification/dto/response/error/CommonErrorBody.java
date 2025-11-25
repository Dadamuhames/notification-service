package com.uzumtech.notification.dto.response.error;

import com.uzumtech.notification.exception.ErrorMessages;

public record CommonErrorBody(ErrorMessages code, String message) {
    public static CommonErrorBody of(ErrorMessages code) {
        return new CommonErrorBody(code, code.getMessage());
    }
}