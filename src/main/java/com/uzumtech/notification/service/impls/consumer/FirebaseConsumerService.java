package com.uzumtech.notification.service.impls.consumer;

import com.uzumtech.notification.dto.event.NotificationEvent;
import com.uzumtech.notification.service.ConsumerService;
import com.uzumtech.notification.service.impls.provider.FirebaseProviderService;
import com.uzumtech.notification.constant.KafkaConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FirebaseConsumerService implements ConsumerService<NotificationEvent> {
    private final FirebaseProviderService firebaseProviderService;

    @KafkaListener(topics = KafkaConstants.PUSH_TOPIC, groupId = KafkaConstants.NOTIFICATION_GROUP_ID)
    public void listen(final NotificationEvent event) {
        firebaseProviderService.send(event);
    }
}
