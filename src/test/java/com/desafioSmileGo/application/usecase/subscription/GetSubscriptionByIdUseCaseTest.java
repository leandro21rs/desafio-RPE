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

class GetSubscriptionByIdUseCaseTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private GetSubscriptionByIdUseCase getSubscriptionByIdUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetSubscriptionByIdWithSuccess() {
        // Arrange
        Long subscriptionId = 1L;
        Subscription subscription = Subscription.builder()
                .id(subscriptionId)
                .clientId("12345")
                .plan(Plan.BASIC)
                .status(SubscriptionStatus.ACTIVE)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mockito.when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(subscription));

        // Act
        Subscription result = getSubscriptionByIdUseCase.execute(subscriptionId);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(subscriptionId, result.getId());
        Assertions.assertEquals("12345", result.getClientId());
        Assertions.assertEquals(Plan.BASIC, result.getPlan());
        Assertions.assertEquals(SubscriptionStatus.ACTIVE, result.getStatus());

        Mockito.verify(subscriptionRepository).findById(subscriptionId);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenSubscriptionNotFound() {
        // Arrange
        Long subscriptionId = 999L;
        Mockito.when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> {
            getSubscriptionByIdUseCase.execute(subscriptionId);
        });

        Mockito.verify(subscriptionRepository).findById(subscriptionId);
    }

    @Test
    void shouldGetSubscriptionWithPremiumPlan() {
        // Arrange
        Long subscriptionId = 2L;
        Subscription subscription = Subscription.builder()
                .id(subscriptionId)
                .clientId("67890")
                .plan(Plan.PREMIUM)
                .status(SubscriptionStatus.ACTIVE)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mockito.when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(subscription));

        // Act
        Subscription result = getSubscriptionByIdUseCase.execute(subscriptionId);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(subscriptionId, result.getId());
        Assertions.assertEquals("67890", result.getClientId());
        Assertions.assertEquals(Plan.PREMIUM, result.getPlan());
        Assertions.assertEquals(SubscriptionStatus.ACTIVE, result.getStatus());

        Mockito.verify(subscriptionRepository).findById(subscriptionId);
    }
} 