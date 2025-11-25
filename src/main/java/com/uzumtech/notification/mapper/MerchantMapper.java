package com.uzumtech.notification.mapper;


import com.uzumtech.notification.dto.request.RegistrationRequest;
import com.uzumtech.notification.entity.MerchantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MerchantMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "request.companyName")
    @Mapping(target = "password", source = "encodedPassword")
    MerchantEntity requestToEntity(final RegistrationRequest request, final String encodedPassword);
}
