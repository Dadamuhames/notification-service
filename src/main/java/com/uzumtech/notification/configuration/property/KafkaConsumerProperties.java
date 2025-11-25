package com.uzumtech.notification.configuration.property;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KafkaConsumerProperties {
    private String bootstrapServers;
}
