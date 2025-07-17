package com.desafioSmileGo.infrastructure.persistence.mapper;

import com.desafioSmileGo.domain.model.Subscription;
import com.desafioSmileGo.domain.model.enums.Plan;
import com.desafioSmileGo.domain.model.enums.SubscriptionStatus;
import com.desafioSmileGo.infrastructure.persistence.entity.SubscriptionEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class SubscriptionEntityMapperTest {

    @Test
    void shouldMapSubscriptionToEntity() {
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

        // Act
        SubscriptionEntity result = SubscriptionEntityMapper.toEntity(subscription);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("12345", result.getClientId());
        Assertions.assertEquals(Plan.BASIC, result.getPlan());
        Assertions.assertEquals(SubscriptionStatus.ACTIVE, result.getStatus());
        Assertions.assertNotNull(result.getStartDate());
        Assertions.assertNotNull(result.getEndDate());
        Assertions.assertNotNull(result.getCreatedAt());
        Assertions.assertNotNull(result.getUpdatedAt());
    }

    @Test
    void shouldMapEntityToSubscription() {
        // Arrange
        SubscriptionEntity entity = new SubscriptionEntity();
        entity.setId(2L);
        entity.setClientId("67890");
        entity.setPlan(Plan.PREMIUM);
        entity.setStatus(SubscriptionStatus.CANCELLED);
        entity.setStartDate(LocalDateTime.now());
        entity.setEndDate(LocalDateTime.now().plusMonths(1));
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        // Act
        Subscription result = SubscriptionEntityMapper.toDomain(entity);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2L, result.getId());
        Assertions.assertEquals("67890", result.getClientId());
        Assertions.assertEquals(Plan.PREMIUM, result.getPlan());
        Assertions.assertEquals(SubscriptionStatus.CANCELLED, result.getStatus());
        Assertions.assertNotNull(result.getStartDate());
        Assertions.assertNotNull(result.getEndDate());
        Assertions.assertNotNull(result.getCreatedAt());
        Assertions.assertNotNull(result.getUpdatedAt());
    }

    @Test
    void shouldMapSubscriptionWithEnterprisePlan() {
        // Arrange
        Subscription subscription = Subscription.builder()
                .id(3L)
                .clientId("11111")
                .plan(Plan.ENTERPRISE)
                .status(SubscriptionStatus.ACTIVE)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Act
        SubscriptionEntity result = SubscriptionEntityMapper.toEntity(subscription);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(3L, result.getId());
        Assertions.assertEquals("11111", result.getClientId());
        Assertions.assertEquals(Plan.ENTERPRISE, result.getPlan());
        Assertions.assertEquals(SubscriptionStatus.ACTIVE, result.getStatus());
    }

    @Test
    void shouldMapEntityWithCancelledStatus() {
        // Arrange
        SubscriptionEntity entity = new SubscriptionEntity();
        entity.setId(4L);
        entity.setClientId("22222");
        entity.setPlan(Plan.BASIC);
        entity.setStatus(SubscriptionStatus.CANCELLED);
        entity.setStartDate(LocalDateTime.now());
        entity.setEndDate(LocalDateTime.now().plusMonths(1));
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        // Act
        Subscription result = SubscriptionEntityMapper.toDomain(entity);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(4L, result.getId());
        Assertions.assertEquals("22222", result.getClientId());
        Assertions.assertEquals(Plan.BASIC, result.getPlan());
        Assertions.assertEquals(SubscriptionStatus.CANCELLED, result.getStatus());
    }

    @Test
    void shouldMapSubscriptionWithNullDates() {
        // Arrange
        Subscription subscription = Subscription.builder()
                .id(5L)
                .clientId("33333")
                .plan(Plan.PREMIUM)
                .status(SubscriptionStatus.ACTIVE)
                .startDate(null)
                .endDate(null)
                .createdAt(null)
                .updatedAt(null)
                .build();

        // Act
        SubscriptionEntity result = SubscriptionEntityMapper.toEntity(subscription);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(5L, result.getId());
        Assertions.assertEquals("33333", result.getClientId());
        Assertions.assertEquals(Plan.PREMIUM, result.getPlan());
        Assertions.assertEquals(SubscriptionStatus.ACTIVE, result.getStatus());
        Assertions.assertNull(result.getStartDate());
        Assertions.assertNull(result.getEndDate());
        Assertions.assertNull(result.getCreatedAt());
        Assertions.assertNull(result.getUpdatedAt());
    }
} 