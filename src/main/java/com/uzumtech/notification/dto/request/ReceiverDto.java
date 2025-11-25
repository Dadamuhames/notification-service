package com.uzumtech.notification.dto.request;

import com.uzumtech.notification.validation.EmailConstraint;
import com.uzumtech.notification.validation.PhoneNumberConstraint;

public record ReceiverDto(
    @PhoneNumberConstraint String phone,
    @EmailConstraint String email,
    String firebaseToken) {
}
