package com.uzumtech.notification.service;

import com.uzumtech.notification.entity.PriceEntity;

public interface PriceService {
    PriceEntity findCurrentPrice();
}
