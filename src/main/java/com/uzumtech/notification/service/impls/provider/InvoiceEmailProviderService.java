package com.uzumtech.notification.service.impls.provider;

import com.uzumtech.notification.configuration.property.MailProperties;
import com.uzumtech.notification.dto.event.InvoiceEvent;
import com.uzumtech.notification.service.EmailTemplateService;
import com.uzumtech.notification.service.ProviderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class InvoiceEmailProviderService implements ProviderService<InvoiceEvent> {
    private final EmailTemplateService emailTemplateService;
    private final MailProperties mailProperties;
    private final JavaMailSender mailSender;

    @Override
    public void send(final InvoiceEvent event) throws MessagingException {
        String content = emailTemplateService.getHtmlMessage(event);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(mailProperties.getUsername());
        helper.setTo(event.email());
        helper.setSubject(String.format("Invoice: %s", event.date()));
        helper.setText(content, true);

        mailSender.send(message);
    }
}
