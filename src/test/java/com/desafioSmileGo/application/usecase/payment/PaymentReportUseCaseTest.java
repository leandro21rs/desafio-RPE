package com.desafioSmileGo.application.usecase.payment;

import com.desafioSmileGo.domain.model.enums.PaymentMethod;
import com.desafioSmileGo.domain.model.enums.PaymentStatus;
import com.desafioSmileGo.domain.repository.PaymentRepository;
import com.desafioSmileGo.infrastructure.api.dto.PaymentReportResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

class PaymentReportUseCaseTest {
    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentReportUseCase paymentReportUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnPaymentReportList() {
        // Arrange
        Long subscriptionId = 1L;
        LocalDateTime start = LocalDateTime.now().minusDays(7);
        LocalDateTime end = LocalDateTime.now();

        List<PaymentReportResponse> mockResponse = List.of(
                new PaymentReportResponse(PaymentMethod.CREDIT_CARD, PaymentStatus.APPROVED, 1L, 200.0),
                new PaymentReportResponse(PaymentMethod.PIX, PaymentStatus.REJECTED, 1L, 100.0)
        );

        Mockito.when(paymentRepository.getPaymentReport(subscriptionId, start, end)).thenReturn(mockResponse);

        // Act
        List<PaymentReportResponse> result = paymentReportUseCase.execute(subscriptionId, start, end);

        // Assert
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(PaymentMethod.PIX, result.get(1).getMethod());
    }
}
