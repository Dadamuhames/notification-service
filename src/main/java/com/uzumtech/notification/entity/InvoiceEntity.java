package com.uzumtech.notification.entity;

import com.uzumtech.notification.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoices", uniqueConstraints = {@UniqueConstraint(columnNames = {"merchant_id", "invoice_month", "invoice_year"})})
public class InvoiceEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", updatable = false, foreignKey = @ForeignKey(name = "fk_merchant"), nullable = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    private MerchantEntity merchant;

    @Column(nullable = false)
    private Integer invoiceMonth;

    @Column(nullable = false)
    private Integer invoiceYear;

    @Column(nullable = false)
    private Integer notificationCount;
}
