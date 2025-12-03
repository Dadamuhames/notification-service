package com.uzumtech.notification.service.impls;

import com.uzumtech.notification.entity.PriceEntity;
import com.uzumtech.notification.exception.PriceNotFoundException;
import com.uzumtech.notification.repository.PriceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {
    @Mock
    PriceRepository priceRepository;

    @InjectMocks
    PriceServiceImpl priceService;


    @Test
    @DisplayName("findCurrentPrice - happy flow")
    void findCurrentPrice_HappyFlow() {
        var entity = new PriceEntity();

        when(priceRepository.findCurrentPrice()).thenReturn(Optional.of(entity));

        var result = priceService.findCurrentPrice();

        assertThat(result).isNotNull().isEqualTo(entity);

        verify(priceRepository, times(1)).findCurrentPrice();
    }


    @Test
    @DisplayName("findCurrentPrice - Should throw exception")
    void findCurrentPrice_ShouldThrowExceptionIfNoPrice() {
        when(priceRepository.findCurrentPrice()).thenReturn(Optional.empty());

        assertThatThrownBy(() -> priceService.findCurrentPrice()).isInstanceOf(PriceNotFoundException.class);

        verify(priceRepository, times(1)).findCurrentPrice();
    }
}