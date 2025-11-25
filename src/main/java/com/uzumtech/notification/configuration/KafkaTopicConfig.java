package com.uzumtech.notification.configuration;

import com.uzumtech.notification.configuration.property.KafkaProperties;
import com.uzumtech.notification.utils.KafkaConstants;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;


@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfig {
    private final KafkaProperties kafkaProperties;

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();

        String bootstrapServers = kafkaProperties.getProducer().getBootstrapServers();

        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic smsTopic() {
        return TopicBuilder.name(KafkaConstants.SMS_TOPIC).partitions(10).replicas(1).build();
    }

    @Bean
    public NewTopic emailTopic() {
        return TopicBuilder.name(KafkaConstants.EMAIL_TOPIC).partitions(10).replicas(1).build();
    }

    @Bean
    public NewTopic pushTopic() {
        return TopicBuilder.name(KafkaConstants.PUSH_TOPIC).partitions(10).replicas(1).build();
    }
}
