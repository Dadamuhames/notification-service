package com.uzumtech.notification.component.kafka.publisher.invoice;

import com.uzumtech.notification.constant.KafkaConstants;
import com.uzumtech.notification.dto.event.InvoiceEvent;
import com.uzumtech.notification.component.kafka.KafkaEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaInvoicePublisher implements KafkaEventPublisher<InvoiceEvent> {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publish(final InvoiceEvent event) {
        kafkaTemplate.send(KafkaConstants.INVOICE_EMAIL_TOPIC, event);
    }
}
