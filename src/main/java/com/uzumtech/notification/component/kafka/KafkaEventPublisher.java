package com.uzumtech.notification.component.kafka;

public interface KafkaEventPublisher<E> {
    void publish(final E event);
}
