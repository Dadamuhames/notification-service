package com.uzumtech.notification.service.impls;

import com.uzumtech.notification.constant.enums.WebhookRequestCodes;
import com.uzumtech.notification.constants.TestConstants;
import com.uzumtech.notification.dto.event.WebhookEvent;
import com.uzumtech.notification.dto.request.WebhookRequest;
import com.uzumtech.notification.constant.enums.NotificationStatus;
import com.uzumtech.notification.exception.http.HttpClientException;
import com.uzumtech.notification.exception.http.HttpServerException;
import com.uzumtech.notification.exception.kafka.nontransients.NonTransientException;
import com.uzumtech.notification.exception.kafka.nontransients.WebhookRequestException;
import com.uzumtech.notification.exception.kafka.transients.TransientException;
import com.uzumtech.notification.exception.kafka.transients.WebhookUnavailableException;
import com.uzumtech.notification.mapper.WebhookMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WebhookServiceImplTest {
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private RestClient restClient;

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
        when(restClient.post().uri(TestConstants.WEBHOOK).body(request).retrieve().toBodilessEntity()).thenReturn(response);

        webhookService.sendTerminalStatus(event, TestConstants.WEBHOOK);

        verify(webhookMapper).eventToRequest(event);
    }


    @Test
    @DisplayName("sendTerminalStatus - Throws Non-Transient exception")
    void shouldThrowNonTransientException_when400BadRequest() {
        WebhookEvent event = createWebhookEvent();
        WebhookRequest request = createWebhookRequest();

        when(webhookMapper.eventToRequest(event)).thenReturn(request);
        when(restClient.post().uri(TestConstants.WEBHOOK).body(request).retrieve().toBodilessEntity()).thenThrow(HttpClientException.class);

        assertThatThrownBy(() -> webhookService.sendTerminalStatus(event, TestConstants.WEBHOOK)).isInstanceOf(WebhookRequestException.class);
    }


    @Test
    @DisplayName("sendTerminalStatus - Transient exception")
    void shouldThrowTransientException_when500ServerError() {
        WebhookEvent event = createWebhookEvent();
        WebhookRequest request = createWebhookRequest();

        when(webhookMapper.eventToRequest(event)).thenReturn(request);
        when(restClient.post().uri(TestConstants.WEBHOOK).body(request).retrieve().toBodilessEntity()).thenThrow(HttpServerException.class);

        assertThatThrownBy(() -> webhookService.sendTerminalStatus(event, TestConstants.WEBHOOK)).isInstanceOf(WebhookUnavailableException.class);
    }
}