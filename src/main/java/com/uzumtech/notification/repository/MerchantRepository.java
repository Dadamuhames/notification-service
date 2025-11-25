package com.uzumtech.notification.repository;

import com.uzumtech.notification.entity.MerchantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantRepository extends JpaRepository<MerchantEntity, Long> {
    Optional<MerchantEntity> findByLogin(final String login);

    boolean existsByLogin(final String login);

    boolean existsByTaxNumber(final String taxNumber);
}
