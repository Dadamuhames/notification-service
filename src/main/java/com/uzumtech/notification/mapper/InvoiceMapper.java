package com.uzumtech.notification.mapper;


import com.uzumtech.notification.dto.event.InvoiceEvent;
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
}
