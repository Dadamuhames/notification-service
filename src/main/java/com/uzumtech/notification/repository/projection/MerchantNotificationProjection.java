package com.uzumtech.notification.repository.projection;

public interface MerchantNotificationProjection {
    Long getId();

    String getName();

    String getEmail();

    Integer getSentNotificationCount();
}
