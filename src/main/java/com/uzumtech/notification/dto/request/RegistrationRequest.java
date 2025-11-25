package com.uzumtech.notification.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

public record RegistrationRequest(
    @NotNull @NotEmpty @Length(min = 9, max = 9) String taxNumber,
    @NotNull @NotEmpty String companyName,
    @NotNull @NotEmpty String email,
    @NotNull @NotEmpty String login,
    @NotNull @URL(protocol = "https", message = "must be a valid HTTPS url") String webhook) {}
