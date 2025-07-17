package com.desafioSmileGo.application.usecase.payment;

import com.desafioSmileGo.application.usecase.subscription.GetSubscriptionByIdUseCase;
import com.desafioSmileGo.domain.model.Payment;
import com.desafioSmileGo.domain.model.Subscription;
import com.desafioSmileGo.domain.model.enums.PaymentStatus;
import com.desafioSmileGo.domain.model.enums.Plan;
import com.desafioSmileGo.domain.model.enums.SubscriptionStatus;
import com.desafioSmileGo.domain.repository.PaymentRepository;
import com.desafioSmileGo.infrastructure.api.dto.PaymentStatusEvent;
import com.desafioSmileGo.infrastructure.external.payment.PaymentGatewayClient;
import com.desafioSmileGo.infrastructure.external.payment.PaymentStatusResponse;
import com.desafioSmileGo.infrastructure.messaging.PaymentStatusProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

class CreatePaymentUseCaseTest {
    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private GetSubscriptionByIdUseCase getSubscriptionByIdUseCase;

    @Mock
    private PaymentGatewayClient paymentGatewayClient;

    @Mock
    private PaymentStatusProducer paymentStatusProducer;

    @InjectMocks
    private CreatePaymentUseCase createPaymentUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void CreatedPaymentWithSuccess() {
        // Arrange
        Long subscriptionId = 1L;
        String paymentId = UUID.randomUUID().toString();
        String transactionalIdMock = UUID.randomUUID().toString();

        Subscription subscription = Subscription.builder()
                .id(subscriptionId)
                .clientId("12345")
                .plan(Plan.BASIC)
                .status(SubscriptionStatus.ACTIVE)
                .build();

        Mockito.when(getSubscriptionByIdUseCase.execute(subscriptionId)).thenReturn(subscription);

        Payment paymentMock = Payment.builder()
                .id(paymentId)
                .subscriptionId(subscriptionId)
                .status(PaymentStatus.PENDING)
                .build();

        Mockito.when(paymentRepository.save(Mockito.any(Payment.class))).thenReturn(paymentMock);
        Mockito.when(paymentGatewayClient.process(Mockito.any(Payment.class))).thenReturn(new PaymentStatusResponse("APPROVED", transactionalIdMock));

        Payment payment = createPaymentUseCase.execute(paymentMock);

        Assertions.assertNotNull(payment);
        Assertions.assertEquals(PaymentStatus.APPROVED, payment.getStatus());
        Assertions.assertEquals(subscription.getId(), payment.getSubscriptionId());

        Mockito.verify(paymentRepository).save(Mockito.any(Payment.class));
        Mockito.verify(paymentGatewayClient).process(Mockito.any());
        Mockito.verify(paymentStatusProducer).send(Mockito.any(PaymentStatusEvent.class));
    }
}
