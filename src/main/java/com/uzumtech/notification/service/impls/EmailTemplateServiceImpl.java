package com.uzumtech.notification.service.impls;

import com.uzumtech.notification.dto.event.InvoiceEvent;
import com.uzumtech.notification.service.EmailTemplateService;
import com.uzumtech.notification.utils.CurrencyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class EmailTemplateServiceImpl implements EmailTemplateService {
    private final SpringTemplateEngine templateEngine;
    private final CurrencyUtils currencyUtils;

    @Override
    public String getHtmlMessage(InvoiceEvent event) {
        Map<String, Object> model = new HashMap<>();

        BigDecimal priceUsz = currencyUtils.tiyinToUsz(event.currentPrice());
        BigDecimal total = priceUsz.multiply(BigDecimal.valueOf(event.sentNotificationCount()));

        model.put("event", event);
        model.put("priceUsz", priceUsz);
        model.put("total", total);

        Context context = new Context();
        context.setVariables(model);

        return templateEngine.process("invoiceEmailTemplate.html", context);
    }
}
