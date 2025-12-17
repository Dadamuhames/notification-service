package com.uzumtech.notification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uzumtech.notification.constants.TestConstants;
import com.uzumtech.notification.dto.request.RegistrationRequest;
import com.uzumtech.notification.dto.response.CommonResponse;
import com.uzumtech.notification.dto.response.RegistrationResponse;
import com.uzumtech.notification.constant.enums.Error;
import com.uzumtech.notification.exception.MerchantValidationException;
import com.uzumtech.notification.repository.MerchantRepository;
import com.uzumtech.notification.service.RegisterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerTest {

    @Autowired
    MockMvcTester mockMvcTester;

    @MockitoBean
    RegisterService registerService;

    @MockitoBean
    MerchantRepository merchantRepository;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @DisplayName("/registration - Happy flow")
    void registration_HappyFlow() throws Exception {
        var request = new RegistrationRequest(
            TestConstants.TAX_NUMBER,
            TestConstants.COMPANY_NAME,
            TestConstants.EMAIL,
            TestConstants.LOGIN,
            TestConstants.WEBHOOK
        );

        var response = CommonResponse.of(new RegistrationResponse(TestConstants.MERCHANT_ID, TestConstants.RAW_PASSWORD));

        when(registerService.register(request)).thenReturn(response);

        assertThat(mockMvcTester.post().contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(request))
            .uri("/api/notifications/registration")).hasStatus(HttpStatus.CREATED)
            .hasBodyTextEqualTo(objectMapper.writeValueAsString(response));

        verify(registerService, times(1)).register(request);
    }


    @Test
    @DisplayName("/registration - invalid email")
    void registration_EmailInvalid() throws Exception {
        var request = new RegistrationRequest(
            TestConstants.TAX_NUMBER,
            TestConstants.COMPANY_NAME,
            TestConstants.NOT_EMAIL,
            TestConstants.LOGIN,
            TestConstants.WEBHOOK
        );

        assertThat(mockMvcTester.post().contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(request))
            .uri("/api/notifications/registration")).hasStatus(HttpStatus.BAD_REQUEST);

        verify(registerService, never()).register(any());
    }


    @Test
    @DisplayName("/registration - invalid tax number")
    void registration_TaxNumberInvalid() throws Exception {
        var request = new RegistrationRequest(
            TestConstants.TAX_NUMBER_SHORT,
            TestConstants.COMPANY_NAME,
            TestConstants.EMAIL,
            TestConstants.LOGIN,
            TestConstants.WEBHOOK
        );

        assertThat(mockMvcTester.post().contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(request))
            .uri("/api/notifications/registration")).hasStatus(HttpStatus.BAD_REQUEST);

        verify(registerService, never()).register(any());
    }


    @Test
    @DisplayName("/registration - existing tax number")
    void registration_TaxNumberExists() throws Exception {
        var request = new RegistrationRequest(
            TestConstants.TAX_NUMBER,
            TestConstants.COMPANY_NAME,
            TestConstants.EMAIL,
            TestConstants.LOGIN,
            TestConstants.WEBHOOK
        );

        when(registerService.register(request))
            .thenThrow(new MerchantValidationException(Error.LOGIN_OR_TAX_NUMBER_NOT_UNIQUE));

        assertThat(mockMvcTester.post().contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(request))
            .uri("/api/notifications/registration")).hasStatus(HttpStatus.BAD_REQUEST);

        verify(registerService, times(1)).register(request);
        verify(merchantRepository, never()).save(any());
    }


    @Test
    @DisplayName("/registration - webhook invalid")
    void registration_InvalidWebhook() throws Exception {
        var request = new RegistrationRequest(
            TestConstants.TAX_NUMBER,
            TestConstants.COMPANY_NAME,
            TestConstants.EMAIL,
            TestConstants.LOGIN,
            TestConstants.NOT_URL_WEBHOOK
        );

        assertThat(mockMvcTester.post().contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(request))
            .uri("/api/notifications/registration")).hasStatus(HttpStatus.BAD_REQUEST);

        verify(registerService, never()).register(request);
        verify(merchantRepository, never()).save(any());
    }
}