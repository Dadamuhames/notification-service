package com.uzumtech.notification.configuration.kafka;

import com.uzumtech.notification.configuration.property.KafkaProperties;
import com.uzumtech.notification.dto.event.NotificationEvent;
import com.uzumtech.notification.constant.KafkaConstants;
import com.uzumtech.notification.exception.kafka.nontransients.NonTransientException;
import com.uzumtech.notification.exception.kafka.transients.TransientException;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {
    private final KafkaProperties kafkaProperties;


    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();

        String bootstrapServers = kafkaProperties.getConsumer().getBootstrapServers();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConstants.NOTIFICATION_GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return props;
    }

    @Bean
    public ConsumerFactory<String, NotificationEvent> consumerFactory() {
        JsonDeserializer<NotificationEvent> payloadJsonDeserializer = new JsonDeserializer<>();

        payloadJsonDeserializer.addTrustedPackages(KafkaConstants.TRUSTED_PACKAGE);

        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(), payloadJsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NotificationEvent> kafkaListenerContainerFactory(DefaultErrorHandler errorHandler) {
        ConcurrentKafkaListenerContainerFactory<String, NotificationEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());
        factory.setCommonErrorHandler(errorHandler);

        return factory;
    }

    @Bean
    public DefaultErrorHandler errorHandler(KafkaOperations<Object, Object> template) {
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(new DeadLetterPublishingRecoverer(template), new FixedBackOff(900000L, 3));

        errorHandler.addRetryableExceptions(TransientException.class);
        errorHandler.addNotRetryableExceptions(NonTransientException.class);

        return errorHandler;
    }
}
