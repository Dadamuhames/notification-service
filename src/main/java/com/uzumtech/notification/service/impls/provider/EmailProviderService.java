package com.uzumtech.notification.service.impls.provider;

import com.uzumtech.notification.configuration.property.MailProperties;
import com.uzumtech.notification.dto.event.NotificationEvent;
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
public class EmailProviderService implements ProviderService<NotificationEvent> {
    private final MailProperties mailProperties;
    private final JavaMailSender mailSender;

    @Override
    public void send(final NotificationEvent event) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(mailProperties.getUsername());
        helper.setTo(event.receiverInfo());
        helper.setSubject(String.format("Notification from - %s", event.merchantName()));
        helper.setText(event.text());

        mailSender.send(message);
    }
}
