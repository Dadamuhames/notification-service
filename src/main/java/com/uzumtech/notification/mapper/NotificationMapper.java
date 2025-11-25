package com.uzumtech.notification.mapper;


import com.uzumtech.notification.dto.event.NotificationEvent;
import com.uzumtech.notification.dto.request.NotificationSendRequest;
import com.uzumtech.notification.entity.MerchantEntity;
import com.uzumtech.notification.entity.NotificationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper {

    @Mapping(target = "merchantId", source = "entity.merchant.id")
    NotificationEvent entityToEvent(final NotificationEntity entity);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "merchant", source = "merchant")
    @Mapping(target = "status", constant = "CREATED")
    @Mapping(target = "receiverInfo", expression = "java(getReceiverInfo(request))")
    NotificationEntity requestToNotification(final NotificationSendRequest request, final MerchantEntity merchant);

    default String getReceiverInfo(final NotificationSendRequest request) {
        return switch (request.type()) {
            case SMS -> request.receiver().phone();

            case EMAIL -> request.receiver().email();

            case PUSH -> request.receiver().firebaseToken();
        };
    }
}
