package com.uzumtech.notification.repository;

import com.uzumtech.notification.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PriceRepository extends JpaRepository<PriceEntity, Long> {

    @Query("SELECT p FROM PriceEntity p WHERE p.isActive = true ORDER BY p.createdAt DESC LIMIT 1")
    Optional<PriceEntity> findCurrentPrice();
}
