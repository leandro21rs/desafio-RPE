package com.desafioSmileGo.infrastructure.persistence.adapter;

import com.desafioSmileGo.domain.model.Payment;
import com.desafioSmileGo.domain.model.enums.PaymentMethod;
import com.desafioSmileGo.domain.model.enums.PaymentStatus;
import com.desafioSmileGo.infrastructure.persistence.entity.PaymentEntity;
import com.desafioSmileGo.infrastructure.persistence.repository.JpaPaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class PaymentRepositoryImplTest {

    @Mock
    private JpaPaymentRepository jpaPaymentRepository;

    @InjectMocks
    private PaymentRepositoryImpl paymentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSavePaymentWithSuccess() {
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

        PaymentEntity entity = new PaymentEntity();
        entity.setId("payment-123");
        entity.setSubscriptionId(1L);
        entity.setAmount(100.0);
        entity.setStatus(PaymentStatus.APPROVED);
        entity.setMethod(PaymentMethod.CREDIT_CARD);
        entity.setPaymentDate(LocalDateTime.now());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        Mockito.when(jpaPaymentRepository.save(Mockito.any(PaymentEntity.class))).thenReturn(entity);

        // Act
        Payment result = paymentRepository.save(payment);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("payment-123", result.getId());
        Assertions.assertEquals(1L, result.getSubscriptionId());
        Assertions.assertEquals(100.0, result.getAmount());
        Assertions.assertEquals(PaymentStatus.APPROVED, result.getStatus());
        Assertions.assertEquals(PaymentMethod.CREDIT_CARD, result.getMethod());

        Mockito.verify(jpaPaymentRepository).save(Mockito.any(PaymentEntity.class));
    }

    @Test
    void shouldFindPaymentByIdWithSuccess() {
        // Arrange
        String paymentId = "payment-123";
        PaymentEntity entity = new PaymentEntity();
        entity.setId(paymentId);
        entity.setSubscriptionId(1L);
        entity.setAmount(100.0);
        entity.setStatus(PaymentStatus.APPROVED);
        entity.setMethod(PaymentMethod.CREDIT_CARD);
        entity.setPaymentDate(LocalDateTime.now());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        Mockito.when(jpaPaymentRepository.findById(paymentId)).thenReturn(Optional.of(entity));

        // Act
        Optional<Payment> result = paymentRepository.findById(paymentId);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(paymentId, result.get().getId());
        Assertions.assertEquals(1L, result.get().getSubscriptionId());
        Assertions.assertEquals(100.0, result.get().getAmount());
        Assertions.assertEquals(PaymentStatus.APPROVED, result.get().getStatus());
        Assertions.assertEquals(PaymentMethod.CREDIT_CARD, result.get().getMethod());

        Mockito.verify(jpaPaymentRepository).findById(paymentId);
    }

    @Test
    void shouldReturnEmptyWhenPaymentNotFound() {
        // Arrange
        String paymentId = "payment-not-found";
        Mockito.when(jpaPaymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        // Act
        Optional<Payment> result = paymentRepository.findById(paymentId);

        // Assert
        Assertions.assertFalse(result.isPresent());

        Mockito.verify(jpaPaymentRepository).findById(paymentId);
    }

    @Test
    void shouldFindPaymentsByFiltersWithSuccess() {
        // Arrange
        PaymentEntity entity1 = new PaymentEntity();
        entity1.setId("payment-1");
        entity1.setSubscriptionId(1L);
        entity1.setAmount(100.0);
        entity1.setStatus(PaymentStatus.APPROVED);
        entity1.setMethod(PaymentMethod.CREDIT_CARD);

        PaymentEntity entity2 = new PaymentEntity();
        entity2.setId("payment-2");
        entity2.setSubscriptionId(2L);
        entity2.setAmount(200.0);
        entity2.setStatus(PaymentStatus.DECLINED);
        entity2.setMethod(PaymentMethod.PIX);

        List<PaymentEntity> entities = Arrays.asList(entity1, entity2);

        // Mock do método findByFilters do JpaPaymentRepository
        Mockito.when(jpaPaymentRepository.findByFilters(
                Mockito.eq(null), // id
                Mockito.eq(null), // subscriptionId
                Mockito.eq(null), // status
                Mockito.eq(null), // method
                Mockito.eq(null), // startDate
                Mockito.eq(null)  // endDate
        )).thenReturn(entities);

        // Act
        List<Payment> result = paymentRepository.findByFilters(
                null, null, null, null, null, null
        );

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("payment-1", result.get(0).getId());
        Assertions.assertEquals("payment-2", result.get(1).getId());

        Mockito.verify(jpaPaymentRepository).findByFilters(
                Mockito.eq(null), Mockito.eq(null), Mockito.eq(null),
                Mockito.eq(null), Mockito.eq(null), Mockito.eq(null)
        );
    }
} 