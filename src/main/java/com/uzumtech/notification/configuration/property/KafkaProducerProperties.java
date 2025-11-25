package com.uzumtech.notification.configuration.property;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KafkaProducerProperties {
    private String bootstrapServers;
}
