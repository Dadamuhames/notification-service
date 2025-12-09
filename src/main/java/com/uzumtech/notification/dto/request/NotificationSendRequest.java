package com.uzumtech.notification.dto.request;

import com.uzumtech.notification.constant.enums.NotificationType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record NotificationSendRequest(
    @Valid ReceiverDto receiver,
    @NotNull(message = "notification type required") NotificationType type,
    @NotBlank(message = "notification text required") String text) {

    @AssertTrue(message = "Single receiver info required (phone, sms or firebaseToken) and should match notification 'type'")
    private boolean isReceiverValid() {
        return isValidEmailReceiver() ^ isValidPushReceiver() ^ isValidSmsReceiver();
    }

    private boolean isValidSmsReceiver() {
        return type.equals(NotificationType.SMS) && receiver.phone() != null;
    }

    public boolean isValidEmailReceiver() {
        return type.equals(NotificationType.EMAIL) && receiver.email() != null;
    }

    public boolean isValidPushReceiver() {
        return type.equals(NotificationType.PUSH) && receiver.firebaseToken() != null;
    }
}
