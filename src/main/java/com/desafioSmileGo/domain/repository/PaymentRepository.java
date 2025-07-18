package com.desafioSmileGo.domain.repository;

import com.desafioSmileGo.domain.model.Payment;
import com.desafioSmileGo.domain.model.enums.PaymentMethod;
import com.desafioSmileGo.domain.model.enums.PaymentStatus;
import com.desafioSmileGo.infrastructure.api.dto.PaymentReportResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository {
    Payment save(Payment payment);

    Optional<Payment> findById(String id);

    List<Payment> findByFilters(String id, Long subscriptionId, PaymentStatus status, PaymentMethod method, LocalDateTime startDate, LocalDateTime endDate);

    List<PaymentReportResponse> getPaymentReport(Long subscriptionId, LocalDateTime startDate, LocalDateTime endDate);
}
