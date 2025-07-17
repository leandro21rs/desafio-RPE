package com.desafioSmileGo.infrastructure.persistence.mapper;

import com.desafioSmileGo.domain.model.Payment;
import com.desafioSmileGo.domain.model.enums.PaymentMethod;
import com.desafioSmileGo.domain.model.enums.PaymentStatus;
import com.desafioSmileGo.infrastructure.persistence.entity.PaymentEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class PaymentEntityMapperTest {

    @Test
    void shouldMapPaymentToEntity() {
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
        PaymentEntity result = PaymentEntityMapper.toEntity(payment);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("payment-123", result.getId());
        Assertions.assertEquals(1L, result.getSubscriptionId());
        Assertions.assertEquals(100.0, result.getAmount());
        Assertions.assertEquals(PaymentStatus.APPROVED, result.getStatus());
        Assertions.assertEquals(PaymentMethod.CREDIT_CARD, result.getMethod());
        Assertions.assertNotNull(result.getPaymentDate());
        Assertions.assertNotNull(result.getCreatedAt());
        Assertions.assertNotNull(result.getUpdatedAt());
    }

    @Test
    void shouldMapEntityToPayment() {
        // Arrange
        PaymentEntity entity = new PaymentEntity();
        entity.setId("payment-456");
        entity.setSubscriptionId(2L);
        entity.setAmount(200.0);
        entity.setStatus(PaymentStatus.DECLINED);
        entity.setMethod(PaymentMethod.PIX);
        entity.setPaymentDate(LocalDateTime.now());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        // Act
        Payment result = PaymentEntityMapper.toDomain(entity);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("payment-456", result.getId());
        Assertions.assertEquals(2L, result.getSubscriptionId());
        Assertions.assertEquals(200.0, result.getAmount());
        Assertions.assertEquals(PaymentStatus.DECLINED, result.getStatus());
        Assertions.assertEquals(PaymentMethod.PIX, result.getMethod());
        Assertions.assertNotNull(result.getPaymentDate());
        Assertions.assertNotNull(result.getCreatedAt());
        Assertions.assertNotNull(result.getUpdatedAt());
    }

    @Test
    void shouldMapPaymentWithBoletoMethod() {
        // Arrange
        Payment payment = Payment.builder()
                .id("payment-789")
                .subscriptionId(3L)
                .amount(300.0)
                .status(PaymentStatus.PENDING)
                .method(PaymentMethod.BOLETO)
                .paymentDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Act
        PaymentEntity result = PaymentEntityMapper.toEntity(payment);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("payment-789", result.getId());
        Assertions.assertEquals(3L, result.getSubscriptionId());
        Assertions.assertEquals(300.0, result.getAmount());
        Assertions.assertEquals(PaymentStatus.PENDING, result.getStatus());
        Assertions.assertEquals(PaymentMethod.BOLETO, result.getMethod());
    }

    @Test
    void shouldMapEntityWithErrorStatus() {
        // Arrange
        PaymentEntity entity = new PaymentEntity();
        entity.setId("payment-999");
        entity.setSubscriptionId(4L);
        entity.setAmount(150.0);
        entity.setStatus(PaymentStatus.ERROR);
        entity.setMethod(PaymentMethod.DEBIT_CARD);
        entity.setPaymentDate(LocalDateTime.now());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        // Act
        Payment result = PaymentEntityMapper.toDomain(entity);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("payment-999", result.getId());
        Assertions.assertEquals(4L, result.getSubscriptionId());
        Assertions.assertEquals(150.0, result.getAmount());
        Assertions.assertEquals(PaymentStatus.ERROR, result.getStatus());
        Assertions.assertEquals(PaymentMethod.DEBIT_CARD, result.getMethod());
    }

    @Test
    void shouldMapPaymentWithNullDates() {
        // Arrange
        Payment payment = Payment.builder()
                .id("payment-null")
                .subscriptionId(5L)
                .amount(500.0)
                .status(PaymentStatus.APPROVED)
                .method(PaymentMethod.CREDIT_CARD)
                .paymentDate(null)
                .createdAt(null)
                .updatedAt(null)
                .build();

        // Act
        PaymentEntity result = PaymentEntityMapper.toEntity(payment);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("payment-null", result.getId());
        Assertions.assertEquals(5L, result.getSubscriptionId());
        Assertions.assertEquals(500.0, result.getAmount());
        Assertions.assertEquals(PaymentStatus.APPROVED, result.getStatus());
        Assertions.assertEquals(PaymentMethod.CREDIT_CARD, result.getMethod());
        Assertions.assertNull(result.getPaymentDate());
        Assertions.assertNull(result.getCreatedAt());
        Assertions.assertNull(result.getUpdatedAt());
    }
} 