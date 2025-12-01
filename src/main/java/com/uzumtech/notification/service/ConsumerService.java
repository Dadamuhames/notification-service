package com.uzumtech.notification.service;

public interface ConsumerService<E> {
    void listen(final E event);
}
