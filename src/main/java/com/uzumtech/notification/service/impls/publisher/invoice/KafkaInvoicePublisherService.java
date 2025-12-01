package com.uzumtech.notification.service.impls.publisher.invoice;

import com.uzumtech.notification.constant.KafkaConstants;
import com.uzumtech.notification.dto.event.InvoiceEvent;
import com.uzumtech.notification.service.KafkaEventPublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaInvoicePublisherService implements KafkaEventPublisherService<InvoiceEvent> {
    private final KafkaTemplate<String, InvoiceEvent> kafkaInvoiceTemplate;

    @Override
    public void publish(final InvoiceEvent event) {
        kafkaInvoiceTemplate.send(KafkaConstants.INVOICE_EMAIL_TOPIC, event);
    }
}
