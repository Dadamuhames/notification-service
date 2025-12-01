package com.uzumtech.notification.utils;

import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.UUID;

@Component
public class PasswordUtils {
    public String generatePassword() {
        String prePassword = String.valueOf(UUID.randomUUID());
        return base24Encode(prePassword).substring(0, 18).toLowerCase();
    }

    public String base24Encode(final String prePassword) {
        return Base64.getEncoder().encodeToString(prePassword.getBytes());
    }
}