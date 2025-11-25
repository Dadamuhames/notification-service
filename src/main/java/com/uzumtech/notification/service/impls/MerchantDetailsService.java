package com.uzumtech.notification.service.impls;

import com.uzumtech.notification.exception.ErrorMessages;
import com.uzumtech.notification.exception.LoginNotFoundException;
import com.uzumtech.notification.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MerchantDetailsService implements UserDetailsService {
    private final MerchantRepository merchantRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return merchantRepository.findByLogin(username).orElseThrow(
            () -> new LoginNotFoundException(ErrorMessages.USERNAME_NOT_FOUND)
        );
    }
}
