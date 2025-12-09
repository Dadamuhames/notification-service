package com.uzumtech.notification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoices", uniqueConstraints = {@UniqueConstraint(columnNames = {"merchant_id", "invoice_month", "invoice_year"})})
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", updatable = false, foreignKey = @ForeignKey(name = "fk_merchant"))
    @OnDelete(action = OnDeleteAction.RESTRICT)
    private MerchantEntity merchant;

    @Column(nullable = false)
    private Integer invoiceMonth;

    @Column(nullable = false)
    private Integer invoiceYear;

    @Column(nullable = false)
    private Integer notificationCount;

    @CreationTimestamp
    private OffsetDateTime createdAt;
}
