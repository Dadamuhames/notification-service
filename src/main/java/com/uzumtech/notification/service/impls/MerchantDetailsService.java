package com.uzumtech.notification.service.impls;

import com.uzumtech.notification.constant.ErrorMessages;
import com.uzumtech.notification.exception.LoginNotFoundException;
import com.uzumtech.notification.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MerchantDetailsService implements UserDetailsService {
    private final MerchantRepository merchantRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return merchantRepository.findByLogin(username).orElseThrow(
            () -> new LoginNotFoundException(ErrorMessages.USERNAME_NOT_FOUND)
        );
    }
}
