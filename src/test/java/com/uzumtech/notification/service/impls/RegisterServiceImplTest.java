package com.uzumtech.notification.service.impls;

import com.uzumtech.notification.constants.TestConstants;
import com.uzumtech.notification.dto.request.RegistrationRequest;
import com.uzumtech.notification.dto.response.CommonResponse;
import com.uzumtech.notification.dto.response.RegistrationResponse;
import com.uzumtech.notification.entity.MerchantEntity;
import com.uzumtech.notification.exception.MerchantValidationException;
import com.uzumtech.notification.mapper.MerchantMapper;
import com.uzumtech.notification.repository.MerchantRepository;
import com.uzumtech.notification.utils.PasswordUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterServiceImplTest {

    @Spy
    MerchantMapper merchantMapper;

    @Mock
    MerchantRepository merchantRepository;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @Mock
    PasswordUtils passwordUtils;

    @InjectMocks
    RegisterServiceImpl registerService;

    @Captor
    ArgumentCaptor<MerchantEntity> merchantCaptor;


    @Test
    @DisplayName("register - happy flow")
    void register_HappyFlow() {
        // Arrange
        var request = new RegistrationRequest(TestConstants.TAX_NUMBER, TestConstants.COMPANY_NAME, TestConstants.EMAIL, TestConstants.LOGIN, TestConstants.WEBHOOK);

        var entity = new MerchantEntity();
        var savedMerchant = new MerchantEntity();
        savedMerchant.setId(1L);

        var response = CommonResponse.of(new RegistrationResponse(TestConstants.MERCHANT_ID, TestConstants.RAW_PASSWORD));

        when(merchantRepository.existsByLoginOrTaxNumber(TestConstants.LOGIN, TestConstants.TAX_NUMBER)).thenReturn(false);
        when(passwordEncoder.encode(TestConstants.RAW_PASSWORD)).thenReturn(TestConstants.ENCODED_PASSWORD);
        when(passwordUtils.generatePassword()).thenReturn(TestConstants.RAW_PASSWORD);
        when(merchantMapper.requestToEntity(request, TestConstants.ENCODED_PASSWORD)).thenReturn(entity);
        when(merchantRepository.save(entity)).thenReturn(savedMerchant);

        // Act
        var result = registerService.register(request);

        // Assert
        assertThat(result).isNotNull().isEqualTo(response);

        // Verify
        verify(merchantMapper, times(1)).requestToEntity(request, TestConstants.ENCODED_PASSWORD);
        verify(merchantRepository, times(1)).save(merchantCaptor.capture());
        verify(passwordUtils, times(1)).generatePassword();
        verify(passwordEncoder, times(1)).encode(TestConstants.RAW_PASSWORD);
        assertThat(merchantCaptor.getValue()).isSameAs(entity);
    }


    @Test
    @DisplayName("register - Tax number or Login exists")
    void register_TaxNumberOrLoginExists() {
        var request = new RegistrationRequest(TestConstants.TAX_NUMBER, TestConstants.COMPANY_NAME, TestConstants.EMAIL, TestConstants.LOGIN, TestConstants.WEBHOOK);

        when(merchantRepository.existsByLoginOrTaxNumber(request.login(), request.taxNumber())).thenReturn(true);

        assertThatThrownBy(() -> registerService.register(request)).isInstanceOf(MerchantValidationException.class);

        // verify
        verify(merchantRepository, times(1)).existsByLoginOrTaxNumber(request.login(), request.taxNumber());
        verify(passwordUtils, never()).generatePassword();
        verify(passwordEncoder, never()).encode(anyString());
        verify(merchantMapper, never()).requestToEntity(any(), anyString());
        verify(merchantRepository, never()).save(any());
    }
}