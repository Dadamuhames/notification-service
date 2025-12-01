package com.uzumtech.notification.service.impls;

import com.uzumtech.notification.dto.request.WebhookRequest;
import com.uzumtech.notification.entity.enums.NotificationStatus;
import com.uzumtech.notification.service.WebhookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class WebhookServiceImpl implements WebhookService {
    private final WebClient webClient;

    public WebhookServiceImpl() {
        this.webClient = WebClient.create();
    }

    @Override
    public void sendTerminalStatus(Long notificationId, NotificationStatus status, String webhook) {
        var request = new WebhookRequest(notificationId, status);

        sendRequestToWebhook(webhook, request);
    }

    private void sendRequestToWebhook(final String webhook, final WebhookRequest request) {
        webClient.post().uri(webhook).bodyValue(request).retrieve().bodyToMono(Void.class)
            .doOnError(e ->
                log.error("Webhook: {}, Error: {}", webhook, e.getMessage()))
            .subscribe();
    }
}
