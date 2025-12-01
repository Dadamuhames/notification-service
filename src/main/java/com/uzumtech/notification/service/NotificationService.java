package com.uzumtech.notification.service;

import com.uzumtech.notification.dto.request.NotificationSendRequest;
import com.uzumtech.notification.dto.response.CommonResponse;
import com.uzumtech.notification.dto.response.NotificationSendResponse;
import com.uzumtech.notification.entity.MerchantEntity;
import com.uzumtech.notification.entity.enums.NotificationStatus;

public interface NotificationService {
    CommonResponse<NotificationSendResponse> send(final NotificationSendRequest request, final MerchantEntity merchant);

    void updateStatus(final Long notificationId, final Long merchantId, final NotificationStatus status);
}

