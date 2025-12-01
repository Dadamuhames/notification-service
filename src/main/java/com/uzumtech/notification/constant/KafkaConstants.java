package com.uzumtech.notification.constant;

public class KafkaConstants {
    public static final String SMS_TOPIC = "notifications.sms.topic";
    public static final String EMAIL_TOPIC = "notifications.email.topic";
    public static final String PUSH_TOPIC = "notifications.push.topic";
    public static final String NOTIFICATION_DLQ_TOPIC = "notifications.dlq.topic";

    public static final String INVOICE_EMAIL_TOPIC = "invoice.email.topic";


    public static final String NOTIFICATION_GROUP_ID = "notification.group";
    public static final String INVOICE_GROUP_ID = "invoice.group";

    public static final String TRUSTED_PACKAGE = "com.uzumtech.notification.dto.event";
}
