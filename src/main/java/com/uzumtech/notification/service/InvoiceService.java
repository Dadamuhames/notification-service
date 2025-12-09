package com.uzumtech.notification.service;

import com.uzumtech.notification.repository.projection.MerchantNotificationProjection;

import java.time.OffsetDateTime;
import java.util.List;

public interface InvoiceService {
    void storeInvoices(List<MerchantNotificationProjection> notifications, OffsetDateTime forDate);
}
