package com.uzumtech.notification.service.impls.publisher.webhook;


import com.uzumtech.notification.constant.KafkaConstants;
import com.uzumtech.notification.dto.event.WebhookEvent;
import com.uzumtech.notification.service.KafkaEventPublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebhookPublisherService implements KafkaEventPublisherService<WebhookEvent> {
    private final KafkaTemplate<String, Object> kafkaTemplate;


    @Override
    public void publish(WebhookEvent event) {
        kafkaTemplate.send(KafkaConstants.WEBHOOK_TOPIC, event);
    }
}
