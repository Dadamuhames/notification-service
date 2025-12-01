package com.uzumtech.notification.service.impls.consumer;

import com.uzumtech.notification.constant.KafkaConstants;
import com.uzumtech.notification.dto.event.InvoiceEvent;
import com.uzumtech.notification.service.ConsumerService;
import com.uzumtech.notification.service.impls.provider.EmailProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InvoiceConsumerService implements ConsumerService<InvoiceEvent> {
    private final EmailProviderService emailProviderService;

    @KafkaListener(topics = KafkaConstants.INVOICE_EMAIL_TOPIC, groupId = KafkaConstants.INVOICE_GROUP_ID)
    public void listen(final InvoiceEvent event) {
        log.info("Invoice: {}", event);
        emailProviderService.send(event);
    }
}
