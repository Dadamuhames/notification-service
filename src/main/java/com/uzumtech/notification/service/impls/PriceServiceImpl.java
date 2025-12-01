package com.uzumtech.notification.service.impls;

import com.uzumtech.notification.entity.PriceEntity;
import com.uzumtech.notification.exception.ErrorMessages;
import com.uzumtech.notification.exception.PriceNotFoundException;
import com.uzumtech.notification.repository.PriceRepository;
import com.uzumtech.notification.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {
    private final PriceRepository priceRepository;

    @Override
    public PriceEntity findCurrentPrice() {
        return priceRepository.findCurrentPrice().orElseThrow(
            () -> new PriceNotFoundException(ErrorMessages.PRICE_NOT_FOUND));
    }
}
