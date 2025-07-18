package com.desafioSmileGo.application.usecase.payment;

import com.desafioSmileGo.domain.model.Payment;
import com.desafioSmileGo.domain.model.enums.PaymentMethod;
import com.desafioSmileGo.domain.model.enums.PaymentStatus;
import com.desafioSmileGo.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ListPaymentsUseCase {

    private final PaymentRepository repository;

    public List<Payment> execute(String id, Long subscriptionId, PaymentStatus status, PaymentMethod method, LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findByFilters(id, subscriptionId, status, method, startDate, endDate);
    }
}
