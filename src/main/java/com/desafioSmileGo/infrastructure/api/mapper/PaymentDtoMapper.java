package com.desafioSmileGo.infrastructure.api.mapper;

import com.desafioSmileGo.domain.model.Payment;
import com.desafioSmileGo.domain.model.enums.PaymentMethod;
import com.desafioSmileGo.infrastructure.api.dto.PaymentRequest;
import com.desafioSmileGo.infrastructure.api.dto.PaymentResponse;
import com.desafioSmileGo.infrastructure.persistence.entity.PaymentEntity;

public class PaymentDtoMapper {
    public static Payment toDomain(PaymentRequest req) {
        return Payment.builder()
                .subscriptionId(req.getSubscriptionId())
                .amount(req.getAmount())
                .method(PaymentMethod.valueOf(req.getMethod()))
                .build();

    }

    public static PaymentResponse toResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .subscriptionId(payment.getSubscriptionId())
                .amount(payment.getAmount())
                .status(payment.getStatus().toString())
                .method(payment.getMethod().toString())
                .paymentDate(payment.getPaymentDate())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}
