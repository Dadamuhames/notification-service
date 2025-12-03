package com.uzumtech.notification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uzumtech.notification.constants.TestConstants;
import com.uzumtech.notification.dto.request.NotificationSendRequest;
import com.uzumtech.notification.dto.request.ReceiverDto;
import com.uzumtech.notification.dto.response.CommonResponse;
import com.uzumtech.notification.dto.response.NotificationSendResponse;
import com.uzumtech.notification.entity.MerchantEntity;
import com.uzumtech.notification.entity.enums.NotificationType;
import com.uzumtech.notification.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;


@SpringBootTest
@AutoConfigureMockMvc
class NotificationControllerTest {

    @Autowired
    MockMvcTester mockMvcTester;

    @MockitoBean
    NotificationService notificationService;

    @Autowired
    ObjectMapper objectMapper;

    private MerchantEntity mockMerchant;
    private NotificationSendRequest validRequest;
    private NotificationSendResponse mockResponse;


    @BeforeEach
    void setUp() {
        mockMerchant = new MerchantEntity();
        mockMerchant.setId(1L);
        mockMerchant.setName(TestConstants.COMPANY_NAME);
        mockMerchant.setLogin(TestConstants.LOGIN);
        mockMerchant.setEmail(TestConstants.EMAIL);
        mockMerchant.setPassword(TestConstants.ENCODED_PASSWORD);
        mockMerchant.setTaxNumber(TestConstants.TAX_NUMBER);
        mockMerchant.setWebhook(TestConstants.WEBHOOK);


        var receiverInfo = new ReceiverDto(TestConstants.PHONE, null, null);
        validRequest = new NotificationSendRequest(receiverInfo, NotificationType.SMS, "notification");

        mockResponse = new NotificationSendResponse(1L);
    }

    @Test
    @DisplayName("/sending - Happy Flow")
    void send_HappyFlow() throws Exception {
        var response = CommonResponse.of(mockResponse);

        when(notificationService.send(validRequest, mockMerchant))
            .thenReturn(response);

        assertThat(
            mockMvcTester.post().contentType(MediaType.APPLICATION_JSON)
                .with(user(mockMerchant))
                .content(objectMapper.writeValueAsBytes(validRequest))
                .uri("/api/notifications/sending")
        ).hasStatus(HttpStatus.OK).hasBodyTextEqualTo(objectMapper.writeValueAsString(response));

        verify(notificationService, times(1)).send(validRequest, mockMerchant);
    }

    @Test
    @DisplayName("/sending - Should return 401")
    void send_MerchantUnauthorized() throws Exception {
        var response = CommonResponse.of(mockResponse);

        when(notificationService.send(validRequest, mockMerchant))
            .thenReturn(response);

        assertThat(
            mockMvcTester.post().contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(validRequest))
                .uri("/api/notifications/sending")
        ).hasStatus(HttpStatus.UNAUTHORIZED);

        verify(notificationService, never()).send(validRequest, mockMerchant);
    }
}