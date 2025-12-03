package com.uzumtech.notification.service.impls.consumer;

import com.uzumtech.notification.constant.KafkaConstants;
import com.uzumtech.notification.dto.event.InvoiceEvent;
import com.uzumtech.notification.exception.kafka.transients.EmailProviderUnavailableException;
import com.uzumtech.notification.exception.kafka.transients.TransientException;
import com.uzumtech.notification.service.ConsumerService;
import com.uzumtech.notification.service.impls.provider.InvoiceEmailProviderService;
import jakarta.mail.MessagingException;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class InvoiceConsumerService implements ConsumerService<InvoiceEvent> {
    private final InvoiceEmailProviderService emailProviderService;

    @RetryableTopic(
        attempts = "4",
        backoff = @Backoff(delay = 3000),
        include = {TransientException.class},
        numPartitions = "3",
        replicationFactor = "1"
    )
    @KafkaListener(topics = KafkaConstants.INVOICE_EMAIL_TOPIC, groupId = KafkaConstants.INVOICE_GROUP_ID)
    public void listen(@Payload @Valid final InvoiceEvent event) {
        try {
            emailProviderService.send(event);
        } catch (MessagingException ex) {
            log.error("Invoice email sending failed: {}", ex.getMessage());

            throw new EmailProviderUnavailableException(ex);
        }
    }

    @DltHandler
    public void dltHandler(InvoiceEvent event, @Header(KafkaHeaders.EXCEPTION_MESSAGE) String exceptionMessage) {
        log.error("Event failed: {}, with exception: {}", event, exceptionMessage);
    }
}
