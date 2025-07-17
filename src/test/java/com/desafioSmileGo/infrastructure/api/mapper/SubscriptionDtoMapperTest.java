package com.desafioSmileGo.infrastructure.api.mapper;

import com.desafioSmileGo.domain.model.Subscription;
import com.desafioSmileGo.domain.model.enums.Plan;
import com.desafioSmileGo.domain.model.enums.SubscriptionStatus;
import com.desafioSmileGo.infrastructure.api.dto.SubscriptionRequest;
import com.desafioSmileGo.infrastructure.api.dto.SubscriptionResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class SubscriptionDtoMapperTest {

    @Test
    void shouldMapSubscriptionRequestToDomain() {
        // Arrange
        SubscriptionRequest request = new SubscriptionRequest();
        request.setClientId("12345");
        request.setPlan(Plan.BASIC);
        request.setEndDate(LocalDateTime.now().plusMonths(1));

        // Act
        Subscription result = SubscriptionDtoMapper.toDomain(request);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("12345", result.getClientId());
        Assertions.assertEquals(Plan.BASIC, result.getPlan());
        Assertions.assertNotNull(result.getEndDate());
    }

    @Test
    void shouldMapSubscriptionToResponse() {
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
        SubscriptionResponse result = SubscriptionDtoMapper.toResponse(subscription);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("12345", result.getClientId());
        Assertions.assertEquals(Plan.BASIC, result.getPlan());
        Assertions.assertEquals(subscription.getStatus(), result.getStatus());
        Assertions.assertNotNull(result.getStartDate());
        Assertions.assertNotNull(result.getEndDate());
        Assertions.assertNotNull(result.getCreatedAt());
        Assertions.assertNotNull(result.getUpdatedAt());
    }

    @Test
    void shouldMapSubscriptionWithPremiumPlan() {
        // Arrange
        SubscriptionRequest request = new SubscriptionRequest();
        request.setClientId("67890");
        request.setPlan(Plan.PREMIUM);
        request.setEndDate(LocalDateTime.now().plusMonths(1));

        // Act
        Subscription result = SubscriptionDtoMapper.toDomain(request);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("67890", result.getClientId());
        Assertions.assertEquals(Plan.PREMIUM, result.getPlan());
        Assertions.assertNotNull(result.getEndDate());
    }

    @Test
    void shouldMapSubscriptionWithCancelledStatus() {
        // Arrange
        Subscription subscription = Subscription.builder()
                .id(3L)
                .clientId("33333")
                .plan(Plan.ENTERPRISE)
                .status(SubscriptionStatus.CANCELLED)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Act
        SubscriptionResponse result = SubscriptionDtoMapper.toResponse(subscription);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(3L, result.getId());
        Assertions.assertEquals("33333", result.getClientId());
        Assertions.assertEquals(Plan.ENTERPRISE, result.getPlan());
        Assertions.assertEquals(subscription.getStatus(), result.getStatus());
    }

    @Test
    void shouldMapSubscriptionWithEnterprisePlan() {
        // Arrange
        SubscriptionRequest request = new SubscriptionRequest();
        request.setClientId("11111");
        request.setPlan(Plan.ENTERPRISE);
        request.setEndDate(LocalDateTime.now().plusMonths(1));

        // Act
        Subscription result = SubscriptionDtoMapper.toDomain(request);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("11111", result.getClientId());
        Assertions.assertEquals(Plan.ENTERPRISE, result.getPlan());
        Assertions.assertNotNull(result.getEndDate());
    }

    @Test
    void shouldMapSubscriptionWithNullEndDate() {
        // Arrange
        SubscriptionRequest request = new SubscriptionRequest();
        request.setClientId("22222");
        request.setPlan(Plan.BASIC);
        request.setEndDate(null);

        // Act
        Subscription result = SubscriptionDtoMapper.toDomain(request);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("22222", result.getClientId());
        Assertions.assertEquals(Plan.BASIC, result.getPlan());
        Assertions.assertNull(result.getEndDate());
    }
}
 