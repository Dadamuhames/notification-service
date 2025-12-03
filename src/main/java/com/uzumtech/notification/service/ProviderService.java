package com.uzumtech.notification.service;

public interface ProviderService<T> {
    void send(final T event) throws Exception;
}
