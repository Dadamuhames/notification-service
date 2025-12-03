package com.uzumtech.notification.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CurrencyUtils {

    public BigDecimal tiyinToUsz(final Long tiyinPrice) {
        return BigDecimal.valueOf(tiyinPrice / 100, 2);
    }
}
