package com.desafioSmileGo.application.usecase.payment;

import com.desafioSmileGo.application.usecase.subscription.GetSubscriptionByIdUseCase;
import com.desafioSmileGo.domain.model.Payment;
import com.desafioSmileGo.domain.model.Subscription;
import com.desafioSmileGo.domain.model.enums.PaymentStatus;
import com.desafioSmileGo.domain.repository.PaymentRepository;
import com.desafioSmileGo.infrastructure.api.dto.PaymentStatusEvent;
import com.desafioSmileGo.infrastructure.external.payment.PaymentGatewayClient;
import com.desafioSmileGo.infrastructure.external.payment.PaymentStatusResponse;
import com.desafioSmileGo.infrastructure.messaging.PaymentStatusProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreatePaymentUseCase {

    private final PaymentRepository repository;
    private final GetSubscriptionByIdUseCase getSubscriptionByIdUseCase;
    private final PaymentGatewayClient gatewayClient;
    private final PaymentStatusProducer producer;

    public Payment execute(Payment payment) {
        Subscription subscription = getSubscriptionByIdUseCase.execute(payment.getSubscriptionId());

        payment.setStatus(PaymentStatus.PENDING);
        payment.setSubscriptionId(subscription.getId());

        Payment savedPayment = repository.save(payment);

        PaymentStatusResponse responsePayment = gatewayClient.process(savedPayment);
        savedPayment.setStatus(PaymentStatus.valueOf(responsePayment.getStatus()));

        producer.send(new PaymentStatusEvent(
                savedPayment.getId(),
                savedPayment.getStatus().toString()
        ));

        return savedPayment;
    }
}
