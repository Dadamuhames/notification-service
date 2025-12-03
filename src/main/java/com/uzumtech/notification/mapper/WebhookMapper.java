package com.uzumtech.notification.mapper;


import com.uzumtech.notification.dto.event.WebhookEvent;
import com.uzumtech.notification.dto.request.WebhookRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WebhookMapper {

    @Mapping(target = "content.notificationId", source = "event.id")
    @Mapping(target = "content.status", source = "event.status")
    @Mapping(target = "code", expression = "java(event.requestCode().getCode())")
    @Mapping(target = "message", expression = "java(event.requestCode().getMessage())")
    WebhookRequest eventToRequest(final WebhookEvent event);
}
