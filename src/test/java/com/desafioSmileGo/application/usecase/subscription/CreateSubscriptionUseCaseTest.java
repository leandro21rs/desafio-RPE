package com.desafioSmileGo.application.usecase.subscription;

import com.desafioSmileGo.domain.model.Subscription;
import com.desafioSmileGo.domain.model.enums.Plan;
import com.desafioSmileGo.domain.model.enums.SubscriptionStatus;
import com.desafioSmileGo.domain.repository.SubscriptionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

class CreateSubscriptionUseCaseTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private CreateSubscriptionUseCase createSubscriptionUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateSubscriptionWithSuccess() {
        // Arrange
        Subscription subscription = Subscription.builder()
                .clientId("12345")
                .plan(Plan.BASIC)
                .status(SubscriptionStatus.ACTIVE)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .build();

        Subscription savedSubscription = Subscription.builder()
                .id(1L)
                .clientId("12345")
                .plan(Plan.BASIC)
                .status(SubscriptionStatus.ACTIVE)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mockito.when(subscriptionRepository.save(Mockito.any(Subscription.class))).thenReturn(savedSubscription);

        // Act
        Subscription result = createSubscriptionUseCase.execute(subscription);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("12345", result.getClientId());
        Assertions.assertEquals(Plan.BASIC, result.getPlan());
        Assertions.assertEquals(SubscriptionStatus.ACTIVE, result.getStatus());
        Assertions.assertNotNull(result.getCreatedAt());
        Assertions.assertNotNull(result.getUpdatedAt());

        Mockito.verify(subscriptionRepository).save(Mockito.any(Subscription.class));
    }

    @Test
    void shouldCreateSubscriptionWithPremiumPlan() {
        // Arrange
        Subscription subscription = Subscription.builder()
                .clientId("67890")
                .plan(Plan.PREMIUM)
                .status(SubscriptionStatus.ACTIVE)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .build();

        Subscription savedSubscription = Subscription.builder()
                .id(2L)
                .clientId("67890")
                .plan(Plan.PREMIUM)
                .status(SubscriptionStatus.ACTIVE)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mockito.when(subscriptionRepository.save(Mockito.any(Subscription.class))).thenReturn(savedSubscription);

        // Act
        Subscription result = createSubscriptionUseCase.execute(subscription);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2L, result.getId());
        Assertions.assertEquals("67890", result.getClientId());
        Assertions.assertEquals(Plan.PREMIUM, result.getPlan());
        Assertions.assertEquals(SubscriptionStatus.ACTIVE, result.getStatus());

        Mockito.verify(subscriptionRepository).save(Mockito.any(Subscription.class));
    }
} 