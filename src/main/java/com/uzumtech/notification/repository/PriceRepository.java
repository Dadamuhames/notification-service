package com.uzumtech.notification.repository;

import com.uzumtech.notification.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<PriceEntity, Long> {
}
