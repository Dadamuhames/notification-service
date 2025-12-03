package com.uzumtech.notification.service;

import com.uzumtech.notification.exception.kafka.nontransients.NonTransientException;

public interface EventValidationService<T> {
    void validate(T event) throws NonTransientException;
}
