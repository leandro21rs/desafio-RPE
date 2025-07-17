package com.desafioSmileGo.application.usecase.payment;

import com.desafioSmileGo.domain.model.Payment;
import com.desafioSmileGo.domain.model.enums.PaymentStatus;
import com.desafioSmileGo.domain.repository.PaymentRepository;
import com.desafioSmileGo.exception.NotFoundException;
import com.desafioSmileGo.infrastructure.api.dto.PaymentStatusEvent;
import com.desafioSmileGo.infrastructure.messaging.PaymentStatusProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UpdatePaymentStatusUseCase {

    private final PaymentRepository repository;
    private final PaymentStatusProducer producer;

    public Payment updatePaymentProducer(String paymentId, String status) {
        Payment payment = repository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("Payment not found"));

        payment.setStatus(PaymentStatus.valueOf(status));

        producer.send(new PaymentStatusEvent(
                paymentId,
                status
        ));

        return payment;
    }

    public void execute(String paymentId, PaymentStatus status) {
        Payment payment = repository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("Payment not found"));

        payment.setStatus(status);
        payment.setUpdatedAt(LocalDateTime.now());

        repository.save(payment);
    }
}
