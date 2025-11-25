package com.uzumtech.notification.service.impls;

import com.uzumtech.notification.dto.request.RegistrationRequest;
import com.uzumtech.notification.dto.response.CommonResponse;
import com.uzumtech.notification.dto.response.RegistrationResponse;
import com.uzumtech.notification.entity.MerchantEntity;
import com.uzumtech.notification.exception.ErrorMessages;
import com.uzumtech.notification.exception.MerchantValidationException;
import com.uzumtech.notification.mapper.MerchantMapper;
import com.uzumtech.notification.repository.MerchantRepository;
import com.uzumtech.notification.service.RegisterService;
import com.uzumtech.notification.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    private final MerchantRepository merchantRepository;
    private final PasswordEncoder passwordEncoder;
    private final MerchantMapper merchantMapper;

    @Override
    public CommonResponse<RegistrationResponse> register(final RegistrationRequest request) {
        validateRequest(request);

        String generatedPassword = PasswordUtils.generatePassword();
        String encodedPassword = passwordEncoder.encode(generatedPassword);

        MerchantEntity merchant = merchantMapper.requestToEntity(request, encodedPassword);
        merchant = merchantRepository.save(merchant);

        return CommonResponse.of(new RegistrationResponse(merchant.getId(), generatedPassword));
    }


    private void validateRequest(final RegistrationRequest request) {
        Map<String, ErrorMessages> errorMessages = new HashMap<>();

        boolean loginExists = merchantRepository.existsByLogin(request.login());
        boolean taxNumberExists = merchantRepository.existsByTaxNumber(request.taxNumber());

        if (loginExists) {
            errorMessages.put("login", ErrorMessages.LOGIN_NOT_UNIQUE);
        }

        if (taxNumberExists) {
            errorMessages.put("taxNumber", ErrorMessages.TAX_NUMBER_NOT_UNIQUE);
        }

        if (!errorMessages.isEmpty()) {
            throw new MerchantValidationException(errorMessages);
        }
    }
}
