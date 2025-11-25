package com.uzumtech.notification.service;

import com.uzumtech.notification.dto.request.RegistrationRequest;
import com.uzumtech.notification.dto.response.CommonResponse;
import com.uzumtech.notification.dto.response.RegistrationResponse;

public interface RegisterService {
    CommonResponse<RegistrationResponse> register(final RegistrationRequest request);
}
