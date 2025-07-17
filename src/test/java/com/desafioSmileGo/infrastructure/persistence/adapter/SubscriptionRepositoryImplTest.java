package com.desafioSmileGo.infrastructure.persistence.adapter;

import com.desafioSmileGo.domain.model.Subscription;
import com.desafioSmileGo.domain.model.enums.Plan;
import com.desafioSmileGo.domain.model.enums.SubscriptionStatus;
import com.desafioSmileGo.infrastructure.persistence.entity.SubscriptionEntity;
import com.desafioSmileGo.infrastructure.persistence.repository.JpaSubscriptionRepository;
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

class SubscriptionRepositoryImplTest {

    @Mock
    private JpaSubscriptionRepository jpaSubscriptionRepository;

    @InjectMocks
    private SubscriptionRepositoryImpl subscriptionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveSubscriptionWithSuccess() {
        // Arrange
        Subscription subscription = Subscription.builder()
                .id(1L)
                .clientId("12345")
                .plan(Plan.BASIC)
                .status(SubscriptionStatus.ACTIVE)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        SubscriptionEntity entity = new SubscriptionEntity();
        entity.setId(1L);
        entity.setClientId("12345");
        entity.setPlan(Plan.BASIC);
        entity.setStatus(SubscriptionStatus.ACTIVE);
        entity.setStartDate(LocalDateTime.now());
        entity.setEndDate(LocalDateTime.now().plusMonths(1));
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        Mockito.when(jpaSubscriptionRepository.save(Mockito.any(SubscriptionEntity.class))).thenReturn(entity);

        // Act
        Subscription result = subscriptionRepository.save(subscription);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("12345", result.getClientId());
        Assertions.assertEquals(Plan.BASIC, result.getPlan());
        Assertions.assertEquals(SubscriptionStatus.ACTIVE, result.getStatus());

        Mockito.verify(jpaSubscriptionRepository).save(Mockito.any(SubscriptionEntity.class));
    }

    @Test
    void shouldFindSubscriptionByIdWithSuccess() {
        // Arrange
        Long subscriptionId = 1L;
        SubscriptionEntity entity = new SubscriptionEntity();
        entity.setId(subscriptionId);
        entity.setClientId("12345");
        entity.setPlan(Plan.BASIC);
        entity.setStatus(SubscriptionStatus.ACTIVE);
        entity.setStartDate(LocalDateTime.now());
        entity.setEndDate(LocalDateTime.now().plusMonths(1));
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        Mockito.when(jpaSubscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(entity));

        // Act
        Optional<Subscription> result = subscriptionRepository.findById(subscriptionId);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(subscriptionId, result.get().getId());
        Assertions.assertEquals("12345", result.get().getClientId());
        Assertions.assertEquals(Plan.BASIC, result.get().getPlan());
        Assertions.assertEquals(SubscriptionStatus.ACTIVE, result.get().getStatus());

        Mockito.verify(jpaSubscriptionRepository).findById(subscriptionId);
    }

    @Test
    void shouldReturnEmptyWhenSubscriptionNotFound() {
        // Arrange
        Long subscriptionId = 999L;
        Mockito.when(jpaSubscriptionRepository.findById(subscriptionId)).thenReturn(Optional.empty());

        // Act
        Optional<Subscription> result = subscriptionRepository.findById(subscriptionId);

        // Assert
        Assertions.assertFalse(result.isPresent());

        Mockito.verify(jpaSubscriptionRepository).findById(subscriptionId);
    }

    @Test
    void shouldFindAllSubscriptionsWithSuccess() {
        // Arrange
        SubscriptionEntity entity1 = new SubscriptionEntity();
        entity1.setId(1L);
        entity1.setClientId("12345");
        entity1.setPlan(Plan.BASIC);
        entity1.setStatus(SubscriptionStatus.ACTIVE);

        SubscriptionEntity entity2 = new SubscriptionEntity();
        entity2.setId(2L);
        entity2.setClientId("67890");
        entity2.setPlan(Plan.PREMIUM);
        entity2.setStatus(SubscriptionStatus.CANCELLED);

        List<SubscriptionEntity> entities = Arrays.asList(entity1, entity2);

        Mockito.when(jpaSubscriptionRepository.findAll()).thenReturn(entities);

        // Act
        List<Subscription> result = subscriptionRepository.findAll();

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(1L, result.get(0).getId());
        Assertions.assertEquals(2L, result.get(1).getId());

        Mockito.verify(jpaSubscriptionRepository).findAll();
    }

    @Test
    void shouldSaveSubscriptionWithPremiumPlan() {
        // Arrange
        Subscription subscription = Subscription.builder()
                .id(2L)
                .clientId("67890")
                .plan(Plan.PREMIUM)
                .status(SubscriptionStatus.ACTIVE)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        SubscriptionEntity entity = new SubscriptionEntity();
        entity.setId(2L);
        entity.setClientId("67890");
        entity.setPlan(Plan.PREMIUM);
        entity.setStatus(SubscriptionStatus.ACTIVE);
        entity.setStartDate(LocalDateTime.now());
        entity.setEndDate(LocalDateTime.now().plusMonths(1));
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        Mockito.when(jpaSubscriptionRepository.save(Mockito.any(SubscriptionEntity.class))).thenReturn(entity);

        // Act
        Subscription result = subscriptionRepository.save(subscription);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2L, result.getId());
        Assertions.assertEquals("67890", result.getClientId());
        Assertions.assertEquals(Plan.PREMIUM, result.getPlan());
        Assertions.assertEquals(SubscriptionStatus.ACTIVE, result.getStatus());

        Mockito.verify(jpaSubscriptionRepository).save(Mockito.any(SubscriptionEntity.class));
    }
} 