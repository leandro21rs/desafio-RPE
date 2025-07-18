package com.desafioSmileGo.application.usecase.payment;

import com.desafioSmileGo.domain.model.Payment;
import com.desafioSmileGo.domain.model.enums.PaymentMethod;
import com.desafioSmileGo.domain.model.enums.PaymentStatus;
import com.desafioSmileGo.domain.repository.PaymentRepository;
import com.desafioSmileGo.exception.NotFoundException;
import com.desafioSmileGo.infrastructure.api.dto.PaymentStatusEvent;
import com.desafioSmileGo.infrastructure.messaging.PaymentStatusProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

class UpdatePaymentStatusUseCaseTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentStatusProducer paymentStatusProducer;

    @InjectMocks
    private UpdatePaymentStatusUseCase updatePaymentStatusUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldUpdatePaymentStatusWithSuccess() {
        // Arrange
        String paymentId = "payment-123";
        String newStatus = "APPROVED";

        Payment existingPayment = Payment.builder()
                .id(paymentId)
                .subscriptionId(1L)
                .amount(100.0)
                .status(PaymentStatus.PENDING)
                .method(PaymentMethod.CREDIT_CARD)
                .paymentDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Payment updatedPayment = Payment.builder()
                .id(paymentId)
                .subscriptionId(1L)
                .amount(100.0)
                .status(PaymentStatus.APPROVED)
                .method(PaymentMethod.CREDIT_CARD)
                .paymentDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mockito.when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(existingPayment));
        Mockito.when(paymentRepository.save(Mockito.any(Payment.class))).thenReturn(updatedPayment);

        // Act
        Payment result = updatePaymentStatusUseCase.updatePaymentProducer(paymentId, newStatus);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(paymentId, result.getId());
        Assertions.assertEquals(PaymentStatus.APPROVED, result.getStatus());
        Assertions.assertEquals(1L, result.getSubscriptionId());
        Assertions.assertEquals(100.0, result.getAmount());

        Mockito.verify(paymentRepository).findById(paymentId);
        Mockito.verify(paymentStatusProducer).send(Mockito.any(PaymentStatusEvent.class));
    }

    @Test
    void shouldUpdatePaymentStatusToDeclined() {
        // Arrange
        String paymentId = "payment-456";
        String newStatus = "DECLINED";

        Payment existingPayment = Payment.builder()
                .id(paymentId)
                .subscriptionId(2L)
                .amount(200.0)
                .status(PaymentStatus.PENDING)
                .method(PaymentMethod.PIX)
                .paymentDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Payment updatedPayment = Payment.builder()
                .id(paymentId)
                .subscriptionId(2L)
                .amount(200.0)
                .status(PaymentStatus.DECLINED)
                .method(PaymentMethod.PIX)
                .paymentDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mockito.when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(existingPayment));
        Mockito.when(paymentRepository.save(Mockito.any(Payment.class))).thenReturn(updatedPayment);

        // Act
        Payment result = updatePaymentStatusUseCase.updatePaymentProducer(paymentId, newStatus);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(paymentId, result.getId());
        Assertions.assertEquals(PaymentStatus.DECLINED, result.getStatus());
        Assertions.assertEquals(2L, result.getSubscriptionId());
        Assertions.assertEquals(200.0, result.getAmount());

        Mockito.verify(paymentRepository).findById(paymentId);
        Mockito.verify(paymentStatusProducer).send(Mockito.any(PaymentStatusEvent.class));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenPaymentNotFound() {
        // Arrange
        String paymentId = "payment-not-found";
        String newStatus = "APPROVED";

        Mockito.when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> {
            updatePaymentStatusUseCase.updatePaymentProducer(paymentId, newStatus);
        });

        Mockito.verify(paymentRepository).findById(paymentId);
        Mockito.verify(paymentStatusProducer, Mockito.never()).send(Mockito.any(PaymentStatusEvent.class));
    }

    @Test
    void shouldUpdatePaymentStatusToError() {
        // Arrange
        String paymentId = "payment-789";
        String newStatus = "ERROR";

        Payment existingPayment = Payment.builder()
                .id(paymentId)
                .subscriptionId(3L)
                .amount(150.0)
                .status(PaymentStatus.PENDING)
                .method(PaymentMethod.BOLETO)
                .paymentDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Payment updatedPayment = Payment.builder()
                .id(paymentId)
                .subscriptionId(3L)
                .amount(150.0)
                .status(PaymentStatus.ERROR)
                .method(PaymentMethod.BOLETO)
                .paymentDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mockito.when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(existingPayment));
        Mockito.when(paymentRepository.save(Mockito.any(Payment.class))).thenReturn(updatedPayment);

        // Act
        Payment result = updatePaymentStatusUseCase.updatePaymentProducer(paymentId, newStatus);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(paymentId, result.getId());
        Assertions.assertEquals(PaymentStatus.ERROR, result.getStatus());
        Assertions.assertEquals(3L, result.getSubscriptionId());
        Assertions.assertEquals(150.0, result.getAmount());

        Mockito.verify(paymentRepository).findById(paymentId);
        Mockito.verify(paymentStatusProducer).send(Mockito.any(PaymentStatusEvent.class));
    }

    @Test
    void shouldUpdatePayment() {
        // Arrange
        Payment paymentMock = new Payment();
        paymentMock.setId("MOCK");
        paymentMock.setStatus(PaymentStatus.PENDING);

        Mockito.when(paymentRepository.findById(Mockito.any())).thenReturn(Optional.of(paymentMock));

        //Act
        Payment payment = updatePaymentStatusUseCase.execute("MOCK", PaymentStatus.APPROVED);

        //Assert
        Assertions.assertNotNull(payment);
        Assertions.assertEquals(PaymentStatus.APPROVED, payment.getStatus());

        Mockito.verify(paymentRepository).findById("MOCK");
    }
} 