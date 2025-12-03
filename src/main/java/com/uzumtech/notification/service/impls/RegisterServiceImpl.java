package com.uzumtech.notification.service.impls;

import com.uzumtech.notification.dto.request.RegistrationRequest;
import com.uzumtech.notification.dto.response.CommonResponse;
import com.uzumtech.notification.dto.response.RegistrationResponse;
import com.uzumtech.notification.entity.MerchantEntity;
import com.uzumtech.notification.constant.ErrorMessages;
import com.uzumtech.notification.exception.MerchantValidationException;
import com.uzumtech.notification.mapper.MerchantMapper;
import com.uzumtech.notification.repository.MerchantRepository;
import com.uzumtech.notification.service.RegisterService;
import com.uzumtech.notification.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    private final MerchantRepository merchantRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordUtils passwordUtils;
    private final MerchantMapper merchantMapper;

    @Override
    @Transactional
    public CommonResponse<RegistrationResponse> register(final RegistrationRequest request) {
        validateRequest(request);

        String generatedPassword = passwordUtils.generatePassword();
        String encodedPassword = passwordEncoder.encode(generatedPassword);

        MerchantEntity merchant = merchantMapper.requestToEntity(request, encodedPassword);
        merchant = merchantRepository.save(merchant);

        return CommonResponse.of(new RegistrationResponse(merchant.getId(), generatedPassword));
    }


    private void validateRequest(final RegistrationRequest request) {
        boolean loginOrTaxNumberExists = merchantRepository.existsByLoginOrTaxNumber(request.login(), request.taxNumber());

        if (loginOrTaxNumberExists) {
            throw new MerchantValidationException(ErrorMessages.LOGIN_OR_TAX_NUMBER_NOT_UNIQUE);
        }
    }
}
