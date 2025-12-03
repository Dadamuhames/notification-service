package com.uzumtech.notification.service;

public interface ConsumerService<E> {
    void listen(final E event);

    void dltHandler(E event, String exceptionMessage);
}
