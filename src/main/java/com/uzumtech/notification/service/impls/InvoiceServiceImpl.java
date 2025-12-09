package com.uzumtech.notification.service.impls;

import com.uzumtech.notification.entity.InvoiceEntity;
import com.uzumtech.notification.entity.MerchantEntity;
import com.uzumtech.notification.entity.NotificationEntity;
import com.uzumtech.notification.mapper.InvoiceMapper;
import com.uzumtech.notification.repository.InvoiceRepository;
import com.uzumtech.notification.repository.projection.MerchantNotificationProjection;
import com.uzumtech.notification.service.InvoiceService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final EntityManager entityManager;
    private final InvoiceMapper invoiceMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void storeInvoices(List<MerchantNotificationProjection> notifications, OffsetDateTime forDate) {
        List<InvoiceEntity> invoices = notifications.stream().map(n -> {
            MerchantEntity merchant = entityManager.getReference(MerchantEntity.class, n.getId());
            return invoiceMapper.projectionToEntity(n, merchant, forDate);
        }).toList();

        invoiceRepository.saveAll(invoices);
    }
}
