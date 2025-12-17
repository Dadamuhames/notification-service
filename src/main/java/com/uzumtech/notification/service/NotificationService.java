package com.uzumtech.notification.service;

import com.uzumtech.notification.dto.request.NotificationSendRequest;
import com.uzumtech.notification.dto.response.CommonResponse;
import com.uzumtech.notification.dto.response.NotificationResponse;
import com.uzumtech.notification.dto.response.NotificationSendResponse;
import com.uzumtech.notification.entity.MerchantEntity;
import com.uzumtech.notification.constant.enums.NotificationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

import java.util.List;

public interface NotificationService {
    CommonResponse<NotificationSendResponse> send(final NotificationSendRequest request, final MerchantEntity merchant);

    void updateStatus(final Long notificationId, final NotificationStatus status);

    Page<NotificationResponse> findAllByMerchant(final MerchantEntity merchant, final Pageable pageable);
}

