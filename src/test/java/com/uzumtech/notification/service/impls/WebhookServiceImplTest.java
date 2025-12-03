package com.uzumtech.notification.service.impls;

import com.uzumtech.notification.constant.WebhookRequestCodes;
import com.uzumtech.notification.constants.TestConstants;
import com.uzumtech.notification.dto.event.WebhookEvent;
import com.uzumtech.notification.dto.request.WebhookRequest;
import com.uzumtech.notification.entity.enums.NotificationStatus;
import com.uzumtech.notification.exception.kafka.nontransients.NonTransientException;
import com.uzumtech.notification.exception.kafka.transients.TransientException;
import com.uzumtech.notification.mapper.WebhookMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WebhookServiceImplTest {
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private WebhookMapper webhookMapper;

    @InjectMocks
    private WebhookServiceImpl webhookService;


    public WebhookRequest createWebhookRequest() {
        var content = new WebhookRequest.WebhookContent(1L, NotificationStatus.SENT);

        return WebhookRequest.of(WebhookRequestCodes.SUCCESS, content);
    }


    public WebhookEvent createWebhookEvent() {
        return new WebhookEvent(1L, 1L, NotificationStatus.SENT, WebhookRequestCodes.SUCCESS);
    }

    @Test
    @DisplayName("sendTerminalStatus - Sent on 2xx response")
    void shouldSendWebhookSuccessfully_when2xxResponse() {
        WebhookEvent event = createWebhookEvent();
        WebhookRequest request = createWebhookRequest();
        ResponseEntity<Void> response = ResponseEntity.ok().build();

        when(webhookMapper.eventToRequest(event)).thenReturn(request);
        when(restTemplate.postForEntity(TestConstants.WEBHOOK, request, Void.class)).thenReturn(response);


        webhookService.sendTerminalStatus(event, TestConstants.WEBHOOK);

        verify(webhookMapper).eventToRequest(event);
        verify(restTemplate).postForEntity(TestConstants.WEBHOOK, request, Void.class);
    }


    @Test
    @DisplayName("sendTerminalStatus - Throws Non-Transient exception")
    void shouldThrowNonTransientException_when400BadRequest() {
        WebhookEvent event = createWebhookEvent();
        WebhookRequest request = createWebhookRequest();
        ResponseEntity<Void> response = ResponseEntity.badRequest().build();

        when(webhookMapper.eventToRequest(event)).thenReturn(request);
        when(restTemplate.postForEntity(TestConstants.WEBHOOK, request, Void.class)).thenThrow(HttpClientErrorException.class);

        assertThatThrownBy(() -> webhookService.sendTerminalStatus(event, TestConstants.WEBHOOK)).isInstanceOf(NonTransientException.class);

        verify(restTemplate).postForEntity(TestConstants.WEBHOOK, request, Void.class);
    }


    @Test
    @DisplayName("sendTerminalStatus - Transient exception")
    void shouldThrowTransientException_when500ServerError() {
        WebhookEvent event = createWebhookEvent();
        WebhookRequest request = createWebhookRequest();
        ResponseEntity<Void> response = ResponseEntity.internalServerError().build();

        when(webhookMapper.eventToRequest(event)).thenReturn(request);
        when(restTemplate.postForEntity(TestConstants.WEBHOOK, request, Void.class)).thenThrow(HttpServerErrorException.class);

        assertThatThrownBy(() -> webhookService.sendTerminalStatus(event, TestConstants.WEBHOOK)).isInstanceOf(TransientException.class);

        verify(restTemplate).postForEntity(TestConstants.WEBHOOK, request, Void.class);
    }
}