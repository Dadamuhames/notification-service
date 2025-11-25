package com.uzumtech.notification.service;

import com.uzumtech.notification.dto.event.NotificationEvent;
import com.uzumtech.notification.service.impls.EmailProviderService;
import com.uzumtech.notification.service.impls.FirebaseProviderService;
import com.uzumtech.notification.service.impls.SmsProviderService;
import com.uzumtech.notification.utils.KafkaConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final SmsProviderService smsProviderService;
    private final EmailProviderService emailProviderService;
    private final FirebaseProviderService firebaseProviderService;

    @KafkaListener(topics = KafkaConstants.SMS_TOPIC, groupId = KafkaConstants.GROUP_ID)
    public void listenForSms(final NotificationEvent event) {
        smsProviderService.send(event);
    }

    @KafkaListener(topics = KafkaConstants.EMAIL_TOPIC, groupId = KafkaConstants.GROUP_ID)
    public void listenForEmail(final NotificationEvent event) {
        emailProviderService.send(event);
    }

    @KafkaListener(topics = KafkaConstants.PUSH_TOPIC, groupId = KafkaConstants.GROUP_ID)
    public void listenForPush(final NotificationEvent event) {
        firebaseProviderService.send(event);
    }
}
