package com.uzumtech.notification.component.kafka.publisher.webhook;


import com.uzumtech.notification.constant.KafkaConstants;
import com.uzumtech.notification.dto.event.WebhookEvent;
import com.uzumtech.notification.component.kafka.KafkaEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebhookPublisher implements KafkaEventPublisher<WebhookEvent> {
    private final KafkaTemplate<String, Object> kafkaTemplate;


    @Override
    public void publish(WebhookEvent event) {
        kafkaTemplate.send(KafkaConstants.WEBHOOK_TOPIC, event);
    }
}
