package com.uzumtech.notification.dto.response.error;

import com.uzumtech.notification.constant.ErrorMessages;

public record CommonErrorBody(ErrorMessages code, String message, String details) {
    public static CommonErrorBody of(ErrorMessages code) {
        return new CommonErrorBody(code, code.getMessage(), null);
    }

    public static CommonErrorBody of(ErrorMessages code, String detail) {
        return new CommonErrorBody(code, code.getMessage(), detail);
    }
}