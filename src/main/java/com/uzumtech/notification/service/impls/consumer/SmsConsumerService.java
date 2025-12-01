package com.uzumtech.notification.service.impls.consumer;

import com.uzumtech.notification.dto.event.NotificationEvent;
import com.uzumtech.notification.service.ConsumerService;
import com.uzumtech.notification.service.impls.provider.SmsProviderService;
import com.uzumtech.notification.constant.KafkaConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmsConsumerService implements ConsumerService<NotificationEvent> {
    private final SmsProviderService smsProviderService;

    @KafkaListener(topics = KafkaConstants.SMS_TOPIC, groupId = KafkaConstants.NOTIFICATION_GROUP_ID)
    public void listen(final NotificationEvent event) {
        smsProviderService.send(event);
    }
}
