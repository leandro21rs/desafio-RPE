package com.desafioSmileGo.application.usecase.subscription;

import com.desafioSmileGo.domain.model.enums.PaymentMethod;
import com.desafioSmileGo.domain.model.enums.PaymentStatus;
import com.desafioSmileGo.domain.model.enums.Plan;
import com.desafioSmileGo.domain.model.enums.SubscriptionStatus;
import com.desafioSmileGo.domain.repository.PaymentRepository;
import com.desafioSmileGo.domain.repository.SubscriptionRepository;
import com.desafioSmileGo.infrastructure.api.dto.PaymentReportResponse;
import com.desafioSmileGo.infrastructure.api.dto.SubscriptionsReportResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

public class SubscriptionsReportUseCaseTest {
    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SubscriptionsReportUseCase subscriptionsReportUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnSubscriptionsReportList() {
        // Arrange
        String clientId = "1";
        LocalDateTime start = LocalDateTime.now().minusDays(7);
        LocalDateTime end = LocalDateTime.now();

        List<SubscriptionsReportResponse> mockResponse = List.of(
                new SubscriptionsReportResponse(Plan.ENTERPRISE, SubscriptionStatus.ACTIVE, 1L),
                new SubscriptionsReportResponse(Plan.BASIC, SubscriptionStatus.CANCELLED, 1L)
        );

        Mockito.when(subscriptionRepository.getSubscriptionsReport(clientId, start, end)).thenReturn(mockResponse);

        // Act
        List<SubscriptionsReportResponse> result = subscriptionsReportUseCase.execute(clientId, start, end);

        // Assert
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(Plan.BASIC, result.get(1).getPlan());
    }
}
