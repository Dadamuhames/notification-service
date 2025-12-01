package com.uzumtech.notification.dto.request;

import com.uzumtech.notification.validation.EmailConstraint;
import com.uzumtech.notification.validation.PhoneNumberConstraint;

public record ReceiverDto(@PhoneNumberConstraint(message = "Phone number format invalid") String phone,
                          @EmailConstraint(message = "Email format invalid") String email, String firebaseToken) {}
