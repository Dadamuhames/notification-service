package com.uzumtech.notification.utils;

import java.util.Base64;
import java.util.UUID;


public class PasswordUtils {
    public static String generatePassword() {
        String prePassword = String.valueOf(UUID.randomUUID());
        return base24Encode(prePassword).substring(0, 18).toLowerCase();
    }

    public static String base24Encode(final String prePassword) {
        return Base64.getEncoder().encodeToString(prePassword.getBytes());
    }
}