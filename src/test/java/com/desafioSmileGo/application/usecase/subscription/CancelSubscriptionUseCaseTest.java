package com.desafioSmileGo.application.usecase.subscription;

import com.desafioSmileGo.domain.model.Subscription;
import com.desafioSmileGo.domain.model.enums.Plan;
import com.desafioSmileGo.domain.model.enums.SubscriptionStatus;
import com.desafioSmileGo.domain.repository.SubscriptionRepository;
import com.desafioSmileGo.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

class CancelSubscriptionUseCaseTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private CancelSubscriptionUseCase cancelSubscriptionUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCancelSubscriptionWithSuccess() {
        // Arrange
        Long subscriptionId = 1L;

        Subscription existingSubscription = Subscription.builder()
                .id(subscriptionId)
                .clientId("12345")
                .plan(Plan.BASIC)
                .status(SubscriptionStatus.ACTIVE)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Subscription cancelledSubscription = Subscription.builder()
                .id(subscriptionId)
                .clientId("12345")
                .plan(Plan.BASIC)
                .status(SubscriptionStatus.CANCELLED)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mockito.when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(existingSubscription));
        Mockito.when(subscriptionRepository.save(Mockito.any(Subscription.class))).thenReturn(cancelledSubscription);

        // Act
        Subscription result = cancelSubscriptionUseCase.execute(subscriptionId);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(subscriptionId, result.getId());
        Assertions.assertEquals(SubscriptionStatus.CANCELLED, result.getStatus());
        Assertions.assertEquals("12345", result.getClientId());
        Assertions.assertEquals(Plan.BASIC, result.getPlan());

        Mockito.verify(subscriptionRepository).findById(subscriptionId);
        Mockito.verify(subscriptionRepository).save(Mockito.any(Subscription.class));
    }

    @Test
    void shouldCancelPremiumSubscription() {
        // Arrange
        Long subscriptionId = 2L;

        Subscription existingSubscription = Subscription.builder()
                .id(subscriptionId)
                .clientId("67890")
                .plan(Plan.PREMIUM)
                .status(SubscriptionStatus.ACTIVE)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Subscription cancelledSubscription = Subscription.builder()
                .id(subscriptionId)
                .clientId("67890")
                .plan(Plan.PREMIUM)
                .status(SubscriptionStatus.CANCELLED)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mockito.when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(existingSubscription));
        Mockito.when(subscriptionRepository.save(Mockito.any(Subscription.class))).thenReturn(cancelledSubscription);

        // Act
        Subscription result = cancelSubscriptionUseCase.execute(subscriptionId);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(subscriptionId, result.getId());
        Assertions.assertEquals(SubscriptionStatus.CANCELLED, result.getStatus());
        Assertions.assertEquals("67890", result.getClientId());
        Assertions.assertEquals(Plan.PREMIUM, result.getPlan());

        Mockito.verify(subscriptionRepository).findById(subscriptionId);
        Mockito.verify(subscriptionRepository).save(Mockito.any(Subscription.class));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenSubscriptionNotFound() {
        // Arrange
        Long subscriptionId = 999L;

        Mockito.when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> {
            cancelSubscriptionUseCase.execute(subscriptionId);
        });

        Mockito.verify(subscriptionRepository).findById(subscriptionId);
        Mockito.verify(subscriptionRepository, Mockito.never()).save(Mockito.any(Subscription.class));
    }

    @Test
    void shouldCancelAlreadyCancelledSubscription() {
        // Arrange
        Long subscriptionId = 3L;

        Subscription existingSubscription = Subscription.builder()
                .id(subscriptionId)
                .clientId("11111")
                .plan(Plan.ENTERPRISE)
                .status(SubscriptionStatus.CANCELLED)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mockito.when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(existingSubscription));
        Mockito.when(subscriptionRepository.save(Mockito.any(Subscription.class))).thenReturn(existingSubscription);

        // Act
        Subscription result = cancelSubscriptionUseCase.execute(subscriptionId);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(subscriptionId, result.getId());
        Assertions.assertEquals(SubscriptionStatus.CANCELLED, result.getStatus());

        Mockito.verify(subscriptionRepository).findById(subscriptionId);
        Mockito.verify(subscriptionRepository).save(Mockito.any(Subscription.class));
    }
} 