package com.uzumtech.notification.dto.request;

import com.uzumtech.notification.constant.enums.WebhookRequestCodes;
import com.uzumtech.notification.constant.enums.NotificationStatus;

public record WebhookRequest(Integer code, String message, WebhookContent content) {
    public record WebhookContent(Long notificationId, NotificationStatus status) {}

    public static WebhookRequest of(WebhookRequestCodes requestCode, WebhookContent content) {
        return new WebhookRequest(requestCode.getCode(), requestCode.getMessage(), content);
    }
}
