package com.uzumtech.notification.service.impls.provider;

import com.uzumtech.notification.configuration.property.MailProperties;
import com.uzumtech.notification.dto.event.InvoiceEvent;
import com.uzumtech.notification.dto.event.NotificationEvent;
import com.uzumtech.notification.entity.enums.NotificationStatus;
import com.uzumtech.notification.service.EmailTemplateService;
import com.uzumtech.notification.service.NotificationService;
import com.uzumtech.notification.service.ProviderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailProviderService implements ProviderService {
    private final EmailTemplateService emailTemplateService;
    private final NotificationService notificationService;
    private final MailProperties mailProperties;
    private final JavaMailSender mailSender;

    @Override
    @Transactional
    public void send(final NotificationEvent event) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(mailProperties.getUsername());
            helper.setTo(event.receiverInfo());
            helper.setSubject(String.format("Notification from - %s", event.merchantName()));
            helper.setText(event.text());

            mailSender.send(message);

            notificationService.updateStatus(event.id(), event.merchantId(), NotificationStatus.SENT);

        } catch (MessagingException e) {
            log.error("Notification email exception: {}", e.getMessage());
            notificationService.updateStatus(event.id(), event.merchantId(), NotificationStatus.FAILED);
        }
    }


    public void send(final InvoiceEvent event) {
        String content = emailTemplateService.getHtmlMessage(event);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(mailProperties.getUsername());
            helper.setTo(event.email());
            helper.setSubject(String.format("Invoice: %s", event.date()));
            helper.setText(content, true);

            mailSender.send(message);

        } catch (MessagingException e) {
            log.error("Invoice email exception: {}", e.getMessage());
        }
    }
}
