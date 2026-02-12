package com.uzumtech.notification.component.kafka.consumer;

import com.uzumtech.notification.component.kafka.publisher.webhook.WebhookPublisher;
import com.uzumtech.notification.constant.enums.NotificationStatus;
import com.uzumtech.notification.constant.enums.NotificationType;
import com.uzumtech.notification.constant.enums.WebhookRequestCodes;
import com.uzumtech.notification.dto.event.NotificationEvent;
import com.uzumtech.notification.dto.event.WebhookEvent;
import com.uzumtech.notification.exception.kafka.transients.EmailProviderUnavailableException;
import com.uzumtech.notification.service.NotificationService;
import com.uzumtech.notification.service.impls.eventvalidation.NotificationValidationService;
import com.uzumtech.notification.service.impls.provider.EmailProviderService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailEventConsumerTest {
    @Mock
    private NotificationService notificationService;
    @Mock
    private EmailProviderService emailProviderService;
    @Mock
    private NotificationValidationService validationService;
    @Mock
    private WebhookPublisher webhookPublisherService;

    @InjectMocks
    private EmailEventConsumer emailEventConsumer;

    private NotificationEvent mockEvent;

    @BeforeEach
    void setUp() {
        mockEvent = new NotificationEvent(100L, 1L, "name", "Hello Content", "test@example.com", NotificationType.EMAIL);
    }

    @Test
    void listen_SuccessFlow() throws MessagingException {
        emailEventConsumer.listen(mockEvent);

        verify(validationService).validate(mockEvent);
        verify(emailProviderService).send(mockEvent);
        verify(notificationService).updateStatus(mockEvent.id(), NotificationStatus.SENT);

        ArgumentCaptor<WebhookEvent> webhookCaptor = ArgumentCaptor.forClass(WebhookEvent.class);
        verify(webhookPublisherService).publish(webhookCaptor.capture());

        WebhookEvent publishedEvent = webhookCaptor.getValue();
        assertThat(publishedEvent.status()).isEqualTo(NotificationStatus.SENT);
        assertThat(publishedEvent.requestCode()).isEqualTo(WebhookRequestCodes.SUCCESS);
    }

    @Test
    void listen_ErrorFlow() throws MessagingException {
        doThrow(new RuntimeException("Provider Down"))
            .when(emailProviderService).send(mockEvent);

        assertThatThrownBy(() -> emailEventConsumer.listen(mockEvent))
            .isInstanceOf(EmailProviderUnavailableException.class);

        verify(notificationService).updateStatus(mockEvent.id(), NotificationStatus.FAILED);

        ArgumentCaptor<WebhookEvent> webhookCaptor = ArgumentCaptor.forClass(WebhookEvent.class);
        verify(webhookPublisherService).publish(webhookCaptor.capture());

        WebhookEvent publishedEvent = webhookCaptor.getValue();
        assertThat(publishedEvent.status()).isEqualTo(NotificationStatus.FAILED);
        assertThat(publishedEvent.requestCode()).isEqualTo(WebhookRequestCodes.SERVICE_UNAVAILABLE);
    }
}