package com.uzumtech.notification.dto.request;

import com.uzumtech.notification.validation.EmailConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record RegistrationRequest(
    @NotBlank(message = "taxNumber should consists of 9 digits") @Size(min = 9, max = 9, message = "taxNumber should consists of 9 digits") String taxNumber,
    @NotBlank(message = "companyName required") String companyName,
    @NotBlank(message = "email required") @EmailConstraint String email,
    @NotBlank(message = "login required") String login,
    @NotBlank(message = "webhook required") @URL(protocol = "https", message = "must be a valid HTTPS url") String webhook) {}
