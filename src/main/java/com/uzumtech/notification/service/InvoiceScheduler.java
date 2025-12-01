package com.uzumtech.notification.service;


import com.uzumtech.notification.entity.PriceEntity;
import com.uzumtech.notification.mapper.InvoiceMapper;
import com.uzumtech.notification.repository.MerchantRepository;
import com.uzumtech.notification.repository.projection.MerchantNotificationProjection;
import com.uzumtech.notification.service.impls.publisher.invoice.KafkaInvoicePublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class InvoiceScheduler {
    private final MerchantRepository merchantRepository;
    private final KafkaInvoicePublisherService invoicePublisherService;
    private final PriceService priceService;
    private final InvoiceMapper invoiceMapper;

    @Scheduled(cron = "0 0 1 * * *")
    public void sendMonthlyInvoices() {
        PriceEntity priceEntity = priceService.findCurrentPrice();

        // sending at the start of the month so we need end of the prev month to calc invoice
        OffsetDateTime yesterday = OffsetDateTime.now().minusDays(1);

        List<MerchantNotificationProjection> merchants = merchantRepository.findWithInvoicesForMonth(yesterday.getYear(), yesterday.getMonthValue());

        log.info("Merchants for invoice: {}", merchants);

        merchants.parallelStream().forEach(m -> {
            var event = invoiceMapper.projectionToEvent(m, priceEntity.getPrice(), yesterday);
            invoicePublisherService.publish(event);
        });
    }
}
