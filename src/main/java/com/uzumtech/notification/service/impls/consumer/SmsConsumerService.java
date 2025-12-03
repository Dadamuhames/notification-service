package com.uzumtech.notification.service.impls.consumer;

import com.uzumtech.notification.constant.KafkaConstants;
import com.uzumtech.notification.dto.event.NotificationEvent;
import com.uzumtech.notification.service.ConsumerService;
import com.uzumtech.notification.service.impls.provider.SmsProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SmsConsumerService implements ConsumerService<NotificationEvent> {
    private final SmsProviderService smsProviderService;

    @KafkaListener(topics = KafkaConstants.SMS_TOPIC, groupId = KafkaConstants.NOTIFICATION_GROUP_ID)
    public void listen(final NotificationEvent event) {
        smsProviderService.send(event);
    }

    @DltHandler
    public void dltHandler(NotificationEvent event, @Header(KafkaHeaders.EXCEPTION_MESSAGE) String exceptionMessage) {
        log.error("Event failed: {}, with exception: {}", event, exceptionMessage);
    }
}
