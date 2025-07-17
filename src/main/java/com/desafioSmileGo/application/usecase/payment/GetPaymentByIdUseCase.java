package com.desafioSmileGo.application.usecase.payment;

import com.desafioSmileGo.domain.model.Payment;
import com.desafioSmileGo.domain.repository.PaymentRepository;
import com.desafioSmileGo.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetPaymentByIdUseCase {

    private final PaymentRepository repository;

    public Payment execute(String paymentId) {
        return repository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("Subscription not found: " + paymentId));
    }
}
