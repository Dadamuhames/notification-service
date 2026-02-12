package com.uzumtech.notification.component.kafka.consumer;

import com.uzumtech.notification.component.kafka.EventConsumer;
import com.uzumtech.notification.constant.KafkaConstants;
import com.uzumtech.notification.constant.enums.KafkaErrorMessage;
import com.uzumtech.notification.dto.event.WebhookEvent;
import com.uzumtech.notification.entity.MerchantEntity;
import com.uzumtech.notification.exception.kafka.nontransients.MerchantIdInvalidException;
import com.uzumtech.notification.exception.kafka.transients.TransientException;
import com.uzumtech.notification.repository.MerchantRepository;
import com.uzumtech.notification.service.WebhookService;
import com.uzumtech.notification.service.impls.eventvalidation.WebhookEventValidationService;
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
public class WebhookEventConsumer implements EventConsumer<WebhookEvent> {
    private final WebhookEventValidationService validationService;
    private final MerchantRepository merchantRepository;
    private final WebhookService webhookService;

    @RetryableTopic(
        attempts = "4",
        backOff = @BackOff(delay = 3000),
        include = {TransientException.class},
        numPartitions = "3",
        replicationFactor = "1"
    )
    @KafkaListener(topics = KafkaConstants.WEBHOOK_TOPIC, groupId = KafkaConstants.NOTIFICATION_STATUS_GROUP_ID)
    public void listen(@Payload @Valid WebhookEvent event) {
        validationService.validate(event);

        MerchantEntity merchant = merchantRepository.findById(event.merchantId()).orElseThrow(() -> new MerchantIdInvalidException(KafkaErrorMessage.MERCHANT_ID_INVALID));

        webhookService.sendTerminalStatus(event, merchant.getWebhook());
    }

    @DltHandler
    public void dltHandler(WebhookEvent event, @Header(KafkaHeaders.EXCEPTION_MESSAGE) String exceptionMessage) {
        log.error("Event failed: {}, with exception: {}", event, exceptionMessage);
    }
}
