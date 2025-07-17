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

class UpdateSubscriptionUseCaseTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private UpdateSubscriptionUseCase updateSubscriptionUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldUpdateSubscriptionWithSuccess() {
        // Arrange
        Long subscriptionId = 1L;
        Plan newPlan = Plan.PREMIUM;

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

        Subscription updatedData = Subscription.builder()
                .plan(newPlan)
                .build();

        Subscription updatedSubscription = Subscription.builder()
                .id(subscriptionId)
                .clientId("12345")
                .plan(newPlan)
                .status(SubscriptionStatus.ACTIVE)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mockito.when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(existingSubscription));
        Mockito.when(subscriptionRepository.save(Mockito.any(Subscription.class))).thenReturn(updatedSubscription);

        // Act
        Subscription result = updateSubscriptionUseCase.execute(subscriptionId, updatedData);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(subscriptionId, result.getId());
        Assertions.assertEquals(newPlan, result.getPlan());
        Assertions.assertEquals("12345", result.getClientId());
        Assertions.assertEquals(SubscriptionStatus.ACTIVE, result.getStatus());

        Mockito.verify(subscriptionRepository).findById(subscriptionId);
        Mockito.verify(subscriptionRepository).save(Mockito.any(Subscription.class));
    }

    @Test
    void shouldUpdateSubscriptionToEnterprisePlan() {
        // Arrange
        Long subscriptionId = 2L;
        Plan newPlan = Plan.ENTERPRISE;

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

        Subscription updatedData = Subscription.builder()
                .plan(newPlan)
                .build();

        Subscription updatedSubscription = Subscription.builder()
                .id(subscriptionId)
                .clientId("67890")
                .plan(newPlan)
                .status(SubscriptionStatus.ACTIVE)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mockito.when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(existingSubscription));
        Mockito.when(subscriptionRepository.save(Mockito.any(Subscription.class))).thenReturn(updatedSubscription);

        // Act
        Subscription result = updateSubscriptionUseCase.execute(subscriptionId, updatedData);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(subscriptionId, result.getId());
        Assertions.assertEquals(newPlan, result.getPlan());
        Assertions.assertEquals("67890", result.getClientId());
        Assertions.assertEquals(SubscriptionStatus.ACTIVE, result.getStatus());

        Mockito.verify(subscriptionRepository).findById(subscriptionId);
        Mockito.verify(subscriptionRepository).save(Mockito.any(Subscription.class));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenSubscriptionNotFound() {
        // Arrange
        Long subscriptionId = 999L;
        Plan newPlan = Plan.PREMIUM;

        Subscription updatedData = Subscription.builder()
                .plan(newPlan)
                .build();

        Mockito.when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> {
            updateSubscriptionUseCase.execute(subscriptionId, updatedData);
        });

        Mockito.verify(subscriptionRepository).findById(subscriptionId);
        Mockito.verify(subscriptionRepository, Mockito.never()).save(Mockito.any(Subscription.class));
    }
} 