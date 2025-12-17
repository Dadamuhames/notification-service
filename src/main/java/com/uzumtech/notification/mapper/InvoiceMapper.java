package com.uzumtech.notification.mapper;


import com.uzumtech.notification.dto.event.InvoiceEvent;
import com.uzumtech.notification.entity.InvoiceEntity;
import com.uzumtech.notification.entity.MerchantEntity;
import com.uzumtech.notification.entity.NotificationEntity;
import com.uzumtech.notification.repository.projection.MerchantNotificationProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.OffsetDateTime;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvoiceMapper {

    @Mapping(target = "currentPrice", source = "price")
    @Mapping(target = "date", source = "date")
    InvoiceEvent projectionToEvent(final MerchantNotificationProjection projection, final Long price, final OffsetDateTime date);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "merchant", source = "merchant")
    @Mapping(target = "invoiceMonth", expression = "java(date.getMonthValue())")
    @Mapping(target = "invoiceYear", expression = "java(date.getYear())")
    @Mapping(target = "notificationCount", expression = "java(projection.getSentNotificationCount())")
    InvoiceEntity projectionToEntity(final MerchantNotificationProjection projection, final MerchantEntity merchant, final OffsetDateTime date);
}
