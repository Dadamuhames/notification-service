package com.uzumtech.notification.dto.response.error;

public record ErrorResponse<T>(T error) {
    public static <T> ErrorResponse<T> of(T data) {
        return new ErrorResponse<>(data);
    }
}
