package com.uzumtech.notification.service;

import com.uzumtech.notification.dto.event.NotificationEvent;
import com.uzumtech.notification.utils.KafkaConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    public void publish(final NotificationEvent event) {
        switch (event.type()) {
            case SMS -> publishSmsEvent(event);

            case EMAIL -> publishEmailEvent(event);

            case PUSH -> publishPushEvent(event);
        }
    }

    public void publishSmsEvent(final NotificationEvent event) {
        kafkaTemplate.send(KafkaConstants.SMS_TOPIC, event);
    }

    public void publishEmailEvent(final NotificationEvent event) {
        kafkaTemplate.send(KafkaConstants.EMAIL_TOPIC, event);
    }

    public void publishPushEvent(final NotificationEvent event) {
        kafkaTemplate.send(KafkaConstants.PUSH_TOPIC, event);
    }
}
