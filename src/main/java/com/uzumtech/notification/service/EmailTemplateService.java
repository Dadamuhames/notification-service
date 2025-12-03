package com.uzumtech.notification.service;

import com.uzumtech.notification.dto.event.InvoiceEvent;

public interface EmailTemplateService {
    String getHtmlMessage(InvoiceEvent event);
}
