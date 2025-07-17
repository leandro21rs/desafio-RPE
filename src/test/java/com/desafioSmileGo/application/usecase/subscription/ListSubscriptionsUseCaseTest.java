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
import java.util.Arrays;
import java.util.List;

class ListSubscriptionsUseCaseTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private ListSubscriptionsUseCase listSubscriptionsUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldListAllSubscriptionsWithSuccess() {
        // Arrange
        Subscription subscription1 = Subscription.builder()
                .id(1L)
                .clientId("12345")
                .plan(Plan.BASIC)
                .status(SubscriptionStatus.ACTIVE)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Subscription subscription2 = Subscription.builder()
                .id(2L)
                .clientId("67890")
                .plan(Plan.PREMIUM)
                .status(SubscriptionStatus.ACTIVE)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        List<Subscription> subscriptions = Arrays.asList(subscription1, subscription2);
        Mockito.when(subscriptionRepository.findAll()).thenReturn(subscriptions);

        // Act
        List<Subscription> result = listSubscriptionsUseCase.execute();

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(1L, result.get(0).getId());
        Assertions.assertEquals(2L, result.get(1).getId());
        Assertions.assertEquals("12345", result.get(0).getClientId());
        Assertions.assertEquals("67890", result.get(1).getClientId());
        Assertions.assertEquals(Plan.BASIC, result.get(0).getPlan());
        Assertions.assertEquals(Plan.PREMIUM, result.get(1).getPlan());

        Mockito.verify(subscriptionRepository).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenNoSubscriptions() {
        // Arrange
        Mockito.when(subscriptionRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<Subscription> result = listSubscriptionsUseCase.execute();

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());

        Mockito.verify(subscriptionRepository).findAll();
    }

    @Test
    void shouldListSubscriptionsWithDifferentStatuses() {
        // Arrange
        Subscription activeSubscription = Subscription.builder()
                .id(1L)
                .clientId("12345")
                .plan(Plan.BASIC)
                .status(SubscriptionStatus.ACTIVE)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Subscription cancelledSubscription = Subscription.builder()
                .id(2L)
                .clientId("67890")
                .plan(Plan.PREMIUM)
                .status(SubscriptionStatus.CANCELLED)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        List<Subscription> subscriptions = Arrays.asList(activeSubscription, cancelledSubscription);
        Mockito.when(subscriptionRepository.findAll()).thenReturn(subscriptions);

        // Act
        List<Subscription> result = listSubscriptionsUseCase.execute();

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(SubscriptionStatus.ACTIVE, result.get(0).getStatus());
        Assertions.assertEquals(SubscriptionStatus.CANCELLED, result.get(1).getStatus());

        Mockito.verify(subscriptionRepository).findAll();
    }
} 