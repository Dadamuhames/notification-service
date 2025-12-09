package com.uzumtech.notification.service.impls;

import com.uzumtech.notification.service.impls.publisher.notification.KafkaNotificationPublisherService;
import com.uzumtech.notification.dto.event.NotificationEvent;
import com.uzumtech.notification.dto.request.NotificationSendRequest;
import com.uzumtech.notification.dto.response.CommonResponse;
import com.uzumtech.notification.dto.response.NotificationSendResponse;
import com.uzumtech.notification.entity.MerchantEntity;
import com.uzumtech.notification.entity.NotificationEntity;
import com.uzumtech.notification.constant.enums.NotificationStatus;
import com.uzumtech.notification.mapper.NotificationMapper;
import com.uzumtech.notification.repository.NotificationRepository;
import com.uzumtech.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final KafkaNotificationPublisherService kafkaProducer;

    @Override
    @Transactional
    public CommonResponse<NotificationSendResponse> send(final NotificationSendRequest request, final MerchantEntity merchant) {
        NotificationEntity notification = notificationMapper.requestToNotification(request, merchant);

        notification = notificationRepository.save(notification);

        sendToPublisher(notification);

        return CommonResponse.of(new NotificationSendResponse(notification.getId()));
    }


    private void sendToPublisher(final NotificationEntity notification) {
        NotificationEvent event = notificationMapper.entityToEvent(notification);
        kafkaProducer.publish(event);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateStatus(final Long notificationId, final NotificationStatus status) {
        notificationRepository.updateStatus(notificationId, status);
    }
}
