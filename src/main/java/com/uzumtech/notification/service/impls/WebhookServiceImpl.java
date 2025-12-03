package com.uzumtech.notification.service.impls;

import com.uzumtech.notification.dto.event.WebhookEvent;
import com.uzumtech.notification.dto.request.WebhookRequest;
import com.uzumtech.notification.constant.KafkaErrorMessage;
import com.uzumtech.notification.exception.kafka.nontransients.WebhookRequestException;
import com.uzumtech.notification.exception.kafka.transients.WebhookUnavailableException;
import com.uzumtech.notification.mapper.WebhookMapper;
import com.uzumtech.notification.service.WebhookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebhookServiceImpl implements WebhookService {
    private final RestTemplate restTemplate;
    private final WebhookMapper webhookMapper;

    @Override
    public void sendTerminalStatus(final WebhookEvent event, final String webhook) {
        WebhookRequest request = webhookMapper.eventToRequest(event);

        sendRequestToWebhook(webhook, request);
    }

    private void sendRequestToWebhook(final String webhook, final WebhookRequest request) {
        try {
            restTemplate.postForEntity(webhook, request, Void.class);
        } catch (HttpClientErrorException ex) {
            throw new WebhookRequestException(KafkaErrorMessage.WEBHOOK_REQUEST_INVALID);
        } catch (HttpServerErrorException ex) {
            throw new WebhookUnavailableException(KafkaErrorMessage.WEBHOOK_UNAVAILABLE);
        }
    }
}
