package com.uzumtech.notification.repository;

import com.uzumtech.notification.entity.NotificationEntity;
import com.uzumtech.notification.entity.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    @Modifying
    @Query("update NotificationEntity n set n.status = :status where n.id = :id")
    void updateStatus(@Param("id") long id, @Param("status") NotificationStatus status);
}
