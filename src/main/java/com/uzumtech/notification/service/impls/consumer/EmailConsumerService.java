package com.uzumtech.notification.service.impls.consumer;

import com.uzumtech.notification.constant.KafkaConstants;
import com.uzumtech.notification.constant.WebhookRequestCodes;
import com.uzumtech.notification.dto.event.NotificationEvent;
import com.uzumtech.notification.dto.event.WebhookEvent;
import com.uzumtech.notification.entity.enums.NotificationStatus;
import com.uzumtech.notification.exception.kafka.transients.EmailProviderUnavailableException;
import com.uzumtech.notification.exception.kafka.transients.TransientException;
import com.uzumtech.notification.service.ConsumerService;
import com.uzumtech.notification.service.NotificationService;
import com.uzumtech.notification.service.impls.eventvalidation.NotificationValidationService;
import com.uzumtech.notification.service.impls.provider.EmailProviderService;
import com.uzumtech.notification.service.impls.publisher.webhook.WebhookPublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionException;


@Slf4j
@Component
@RequiredArgsConstructor
public class EmailConsumerService implements ConsumerService<NotificationEvent> {
    private final NotificationService notificationService;
    private final EmailProviderService emailProviderService;
    private final NotificationValidationService validationService;
    private final WebhookPublisherService webhookPublisherService;

    @KafkaListener(topics = KafkaConstants.EMAIL_TOPIC, groupId = KafkaConstants.NOTIFICATION_GROUP_ID)
    @RetryableTopic(
        attempts = "4",
        backoff = @Backoff(delay = 3000),
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
