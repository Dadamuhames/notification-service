package com.uzumtech.notification.service.impls;

import com.uzumtech.notification.dto.event.NotificationEvent;
import com.uzumtech.notification.dto.request.NotificationSendRequest;
import com.uzumtech.notification.dto.request.ReceiverDto;
import com.uzumtech.notification.dto.response.CommonResponse;
import com.uzumtech.notification.dto.response.NotificationSendResponse;
import com.uzumtech.notification.entity.MerchantEntity;
import com.uzumtech.notification.entity.NotificationEntity;
import com.uzumtech.notification.constant.enums.NotificationType;
import com.uzumtech.notification.mapper.NotificationMapper;
import com.uzumtech.notification.repository.NotificationRepository;
import com.uzumtech.notification.service.impls.publisher.notification.KafkaNotificationPublisherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Spy
    NotificationMapper notificationMapper;

    @Mock
    NotificationRepository notificationRepository;


    @Mock
    KafkaNotificationPublisherService kafkaNotificationPublisherService;

    @InjectMocks
    NotificationServiceImpl notificationService;

    @Captor
    ArgumentCaptor<NotificationEntity> notificationCaptor;


    @Test
    @DisplayName("send - Happy flow sms")
    void send_HappyFlowSms() {
        var merchant = new MerchantEntity();
        var receiverInfo = new ReceiverDto("998999999999", null, null);
        var request = new NotificationSendRequest(receiverInfo, NotificationType.SMS, "notification");

        var entity = new NotificationEntity();
        var savedEntity = new NotificationEntity();
        savedEntity.setId(1L);

        var response = CommonResponse.of(new NotificationSendResponse(1L));

        var event = new NotificationEvent(
            1L,
            1L,
            "MerchantName",
            "notification",
            "998999999999",
            NotificationType.SMS
        );

        when(notificationMapper.requestToNotification(request, merchant)).thenReturn(entity);
        when(notificationRepository.save(entity)).thenReturn(savedEntity);
        when(notificationMapper.entityToEvent(savedEntity)).thenReturn(event);

        // Act
        var result = notificationService.send(request, merchant);

        // Assert
        assertThat(result).isNotNull().isEqualTo(response);

        // Verify
        verify(notificationMapper, times(1)).requestToNotification(request, merchant);
        verify(notificationRepository, times(1)).save(notificationCaptor.capture());
        verify(notificationMapper, times(1)).entityToEvent(savedEntity);
        verify(kafkaNotificationPublisherService, times(1)).publish(event);
        assertThat(notificationCaptor.getValue()).isSameAs(entity);
    }
}