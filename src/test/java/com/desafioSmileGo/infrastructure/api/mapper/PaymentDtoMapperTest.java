package com.desafioSmileGo.infrastructure.api.mapper;

import com.desafioSmileGo.domain.model.Payment;
import com.desafioSmileGo.domain.model.enums.PaymentMethod;
import com.desafioSmileGo.domain.model.enums.PaymentStatus;
import com.desafioSmileGo.infrastructure.api.dto.PaymentRequest;
import com.desafioSmileGo.infrastructure.api.dto.PaymentResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class PaymentDtoMapperTest {

    @Test
    void shouldMapPaymentRequestToDomain() {
        // Arrange
        PaymentRequest request = new PaymentRequest();
        request.setSubscriptionId(1L);
        request.setAmount(100.0);
        request.setMethod("CREDIT_CARD");

        // Act
        Payment result = PaymentDtoMapper.toDomain(request);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getSubscriptionId());
        Assertions.assertEquals(100.0, result.getAmount());
        Assertions.assertEquals(PaymentMethod.CREDIT_CARD, result.getMethod());
    }

    @Test
    void shouldMapPaymentToResponse() {
        // Arrange
        Payment payment = Payment.builder()
                .id("payment-123")
                .subscriptionId(1L)
                .amount(100.0)
                .status(PaymentStatus.APPROVED)
                .method(PaymentMethod.CREDIT_CARD)
                .paymentDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Act
        PaymentResponse result = PaymentDtoMapper.toResponse(payment);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("payment-123", result.getId());
        Assertions.assertEquals(1L, result.getSubscriptionId());
        Assertions.assertEquals(100.0, result.getAmount());
        Assertions.assertEquals(payment.getStatus().name(), result.getStatus());
        Assertions.assertEquals(payment.getMethod().name(), result.getMethod());
        Assertions.assertNotNull(result.getPaymentDate());
        Assertions.assertNotNull(result.getCreatedAt());
        Assertions.assertNotNull(result.getUpdatedAt());
    }

    @Test
    void shouldMapPaymentWithPixMethod() {
        // Arrange
        PaymentRequest request = new PaymentRequest();
        request.setSubscriptionId(2L);
        request.setAmount(200.0);
        request.setMethod("PIX");

        // Act
        Payment result = PaymentDtoMapper.toDomain(request);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2L, result.getSubscriptionId());
        Assertions.assertEquals(200.0, result.getAmount());
        Assertions.assertEquals(PaymentMethod.PIX, result.getMethod());
    }

    @Test
    void shouldMapPaymentWithDeclinedStatus() {
        // Arrange
        Payment payment = Payment.builder()
                .id("payment-456")
                .subscriptionId(3L)
                .amount(150.0)
                .status(PaymentStatus.DECLINED)
                .method(PaymentMethod.PIX)
                .paymentDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Act
        PaymentResponse result = PaymentDtoMapper.toResponse(payment);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("payment-456", result.getId());
        Assertions.assertEquals(3L, result.getSubscriptionId());
        Assertions.assertEquals(150.0, result.getAmount());
        Assertions.assertEquals(payment.getStatus().name(), result.getStatus());
        Assertions.assertEquals(payment.getMethod().name(), result.getMethod());
    }

    @Test
    void shouldMapPaymentWithBoletoMethod() {
        // Arrange
        PaymentRequest request = new PaymentRequest();
        request.setSubscriptionId(4L);
        request.setAmount(300.0);
        request.setMethod("BOLETO");

        // Act
        Payment result = PaymentDtoMapper.toDomain(request);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(4L, result.getSubscriptionId());
        Assertions.assertEquals(300.0, result.getAmount());
        Assertions.assertEquals(PaymentMethod.BOLETO, result.getMethod());
    }

    @Test
    void shouldMapPaymentWithErrorStatus() {
        // Arrange
        Payment payment = Payment.builder()
                .id("payment-789")
                .subscriptionId(5L)
                .amount(250.0)
                .status(PaymentStatus.ERROR)
                .method(PaymentMethod.BOLETO)
                .paymentDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Act
        PaymentResponse result = PaymentDtoMapper.toResponse(payment);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("payment-789", result.getId());
        Assertions.assertEquals(5L, result.getSubscriptionId());
        Assertions.assertEquals(250.0, result.getAmount());
        Assertions.assertEquals(payment.getStatus().name(), result.getStatus());
        Assertions.assertEquals(payment.getMethod().name(), result.getMethod());
    }
} 