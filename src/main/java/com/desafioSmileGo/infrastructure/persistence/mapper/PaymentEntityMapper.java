package com.desafioSmileGo.infrastructure.persistence.mapper;

import com.desafioSmileGo.domain.model.Payment;
import com.desafioSmileGo.domain.model.Subscription;
import com.desafioSmileGo.infrastructure.persistence.entity.PaymentEntity;
import com.desafioSmileGo.infrastructure.persistence.entity.SubscriptionEntity;

public class PaymentEntityMapper {
    public static PaymentEntity toEntity(Payment p) {
        if (p == null) return null;
        return PaymentEntity.builder()
                .id(p.getId())
                .subscriptionId(p.getSubscriptionId())
                .amount(p.getAmount())
                .status(p.getStatus())
                .method(p.getMethod())
                .paymentDate(p.getPaymentDate())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }

    public static Payment toDomain(PaymentEntity e) {
        if (e == null) return null;
        return Payment.builder()
                .id(e.getId())
                .subscriptionId(e.getSubscriptionId())
                .amount(e.getAmount())
                .status(e.getStatus())
                .method(e.getMethod())
                .paymentDate(e.getPaymentDate())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }
}
