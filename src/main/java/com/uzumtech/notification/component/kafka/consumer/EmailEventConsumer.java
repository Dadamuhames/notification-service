package com.uzumtech.notification.component.kafka.consumer;

import com.uzumtech.notification.constant.KafkaConstants;
import com.uzumtech.notification.constant.enums.WebhookRequestCodes;
import com.uzumtech.notification.dto.event.NotificationEvent;
import com.uzumtech.notification.dto.event.WebhookEvent;
import com.uzumtech.notification.constant.enums.NotificationStatus;
import com.uzumtech.notification.exception.kafka.transients.EmailProviderUnavailableException;
import com.uzumtech.notification.exception.kafka.transients.TransientException;
import com.uzumtech.notification.component.kafka.EventConsumer;
import com.uzumtech.notification.service.NotificationService;
import com.uzumtech.notification.service.impls.eventvalidation.NotificationValidationService;
import com.uzumtech.notification.service.impls.provider.EmailProviderService;
import com.uzumtech.notification.component.kafka.publisher.webhook.WebhookPublisher;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class EmailEventConsumer implements EventConsumer<NotificationEvent> {
    private final NotificationService notificationService;
    private final EmailProviderService emailProviderService;
    private final NotificationValidationService validationService;
    private final WebhookPublisher webhookPublisherService;

    @KafkaListener(topics = KafkaConstants.EMAIL_TOPIC, groupId = KafkaConstants.NOTIFICATION_GROUP_ID)
    @RetryableTopic(
        attempts = "4",
        backOff = @BackOff(delay = 3000),
        include = {TransientException.class},
        numPartitions = "3",
        replicationFactor = "1"
    )
    public void listen(@Payload @Valid NotificationEvent event) {
        validationService.validate(event);

        try {
            emailProviderService.send(event);

            notificationService.updateStatus(event.id(), NotificationStatus.SENT);

            var webhookEvent = new WebhookEvent(event.id(), event.merchantId(), NotificationStatus.SENT, WebhookRequestCodes.SUCCESS);
            webhookPublisherService.publish(webhookEvent);

        } catch (Exception ex) {
            log.error("Email sending failed: {}", ex.getMessage());

            notificationService.updateStatus(event.id(), NotificationStatus.FAILED);

            var webhookEvent = new WebhookEvent(event.id(), event.merchantId(), NotificationStatus.FAILED, WebhookRequestCodes.SERVICE_UNAVAILABLE);

            webhookPublisherService.publish(webhookEvent);

            throw new EmailProviderUnavailableException(ex);
        }
    }

    @DltHandler
    public void dltHandler(NotificationEvent event, @Header(KafkaHeaders.EXCEPTION_MESSAGE) String exceptionMessage) {
        log.error("Event failed: {}, with exception: {}", event, exceptionMessage);
    }
}
