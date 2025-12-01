package com.uzumtech.notification.service.impls.consumer;

import com.uzumtech.notification.constant.KafkaConstants;
import com.uzumtech.notification.dto.event.NotificationEvent;
import com.uzumtech.notification.service.ConsumerService;
import com.uzumtech.notification.service.impls.provider.EmailProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailConsumerService implements ConsumerService<NotificationEvent> {
    private final EmailProviderService emailProviderService;

    @KafkaListener(topics = KafkaConstants.EMAIL_TOPIC, groupId = KafkaConstants.NOTIFICATION_GROUP_ID)
    public void listen(@Payload @Valid final NotificationEvent event) {
        emailProviderService.send(event);
    }
}
