package com.uzumtech.notification.repository;

import com.uzumtech.notification.entity.NotificationEntity;
import com.uzumtech.notification.constant.enums.NotificationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    @Modifying
    @Query("update NotificationEntity n set n.status = :status, n.updatedAt = CURRENT_TIMESTAMP where n.id = :id")
    void updateStatus(@Param("id") long id, @Param("status") NotificationStatus status);

    @Query("SELECT COUNT(n.id) > 0 FROM NotificationEntity n WHERE n.id = ?1 AND n.status != 'SENT'")
    boolean existsByIdAndStatusNotSent(Long id);

    Page<NotificationEntity> findAllByMerchantId(Long merchantId, Pageable pageable);
}
