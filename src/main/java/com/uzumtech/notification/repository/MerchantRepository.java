package com.uzumtech.notification.repository;

import com.uzumtech.notification.entity.MerchantEntity;
import com.uzumtech.notification.repository.projection.MerchantNotificationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MerchantRepository extends JpaRepository<MerchantEntity, Long> {
    Optional<MerchantEntity> findByLogin(final String login);

    boolean existsByLoginOrTaxNumber(final String login, final String taxNumber);

    @Query("SELECT DISTINCT m.id AS id, m.name AS name, m.email AS email, COUNT(n.id) AS sentNotificationCount " +
        "FROM MerchantEntity m " +
        "INNER JOIN m.notifications n " +
        "ON YEAR(n.createdAt) = :year AND MONTH(n.createdAt) = :month " +
        "AND n.status = 'SENT' AND n.type = 'SMS' " +
        "GROUP BY m.id, m.name, m.email")
    List<MerchantNotificationProjection> findWithInvoicesForMonth(@Param("year") int year, @Param("month") int month);
}
