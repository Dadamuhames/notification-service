package com.uzumtech.notification.entity;


import com.uzumtech.notification.constant.enums.NotificationStatus;
import com.uzumtech.notification.constant.enums.NotificationType;
import com.uzumtech.notification.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;


@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notifications", indexes = @Index(columnList = "merchant_id"))
public class NotificationEntity extends BaseEntity {

    @Column(nullable = false)
    private String text;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(columnDefinition = "type", nullable = false)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(columnDefinition = "status", nullable = false)
    private NotificationStatus status;

    @Column(nullable = false)
    private String receiverInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", updatable = false, foreignKey = @ForeignKey(name = "fk_merchant"), nullable = false)
    private MerchantEntity merchant;
}
