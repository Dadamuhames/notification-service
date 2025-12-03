package com.uzumtech.notification.service;

public interface KafkaEventPublisherService<E> {
    void publish(final E event);
}
