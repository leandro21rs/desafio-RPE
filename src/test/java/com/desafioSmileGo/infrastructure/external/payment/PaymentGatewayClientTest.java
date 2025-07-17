package com.desafioSmileGo.infrastructure.external.payment;

import com.desafioSmileGo.domain.model.Payment;
import com.desafioSmileGo.domain.model.enums.PaymentMethod;
import com.desafioSmileGo.domain.model.enums.PaymentStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Map;

class PaymentGatewayClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PaymentGatewayClient paymentGatewayClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldProcessPaymentWithSuccess() {
        // Arrange
        Payment payment = Payment.builder()
                .id("payment-123")
                .subscriptionId(1L)
                .amount(100.0)
                .status(PaymentStatus.PENDING)
                .method(PaymentMethod.CREDIT_CARD)
                .paymentDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Map<String, Object> responseBody = Map.of(
                "status", "APPROVED",
                "transactionId", "txn-123"
        );
        ResponseEntity<Map> response = ResponseEntity.ok(responseBody);

        Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(), Mockito.eq(Map.class)))
                .thenReturn(response);

        // Act
        PaymentStatusResponse result = paymentGatewayClient.process(payment);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("APPROVED", result.getStatus());
        Assertions.assertEquals("txn-123", result.getTransactionId());

        Mockito.verify(restTemplate).postForEntity(Mockito.anyString(), Mockito.any(), Mockito.eq(Map.class));
    }

    @Test
    void shouldProcessPaymentWithDeclinedStatus() {
        // Arrange
        Payment payment = Payment.builder()
                .id("payment-456")
                .subscriptionId(2L)
                .amount(200.0)
                .status(PaymentStatus.PENDING)
                .method(PaymentMethod.PIX)
                .paymentDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Map<String, Object> responseBody = Map.of(
                "status", "DECLINED",
                "transactionId", "txn-456"
        );
        ResponseEntity<Map> response = ResponseEntity.ok(responseBody);

        Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(), Mockito.eq(Map.class)))
                .thenReturn(response);

        // Act
        PaymentStatusResponse result = paymentGatewayClient.process(payment);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("DECLINED", result.getStatus());
        Assertions.assertEquals("txn-456", result.getTransactionId());

        Mockito.verify(restTemplate).postForEntity(Mockito.anyString(), Mockito.any(), Mockito.eq(Map.class));
    }

    @Test
    void shouldProcessPaymentWithErrorStatus() {
        // Arrange
        Payment payment = Payment.builder()
                .id("payment-789")
                .subscriptionId(3L)
                .amount(150.0)
                .status(PaymentStatus.PENDING)
                .method(PaymentMethod.BOLETO)
                .paymentDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Map<String, Object> responseBody = Map.of(
                "status", "ERROR",
                "transactionId", "txn-789"
        );
        ResponseEntity<Map> response = ResponseEntity.ok(responseBody);

        Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(), Mockito.eq(Map.class)))
                .thenReturn(response);

        // Act
        PaymentStatusResponse result = paymentGatewayClient.process(payment);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("ERROR", result.getStatus());
        Assertions.assertEquals("txn-789", result.getTransactionId());

        Mockito.verify(restTemplate).postForEntity(Mockito.anyString(), Mockito.any(), Mockito.eq(Map.class));
    }

    @Test
    void shouldProcessPaymentWithRejectedStatus() {
        // Arrange
        Payment payment = Payment.builder()
                .id("payment-999")
                .subscriptionId(4L)
                .amount(300.0)
                .status(PaymentStatus.PENDING)
                .method(PaymentMethod.DEBIT_CARD)
                .paymentDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Map<String, Object> responseBody = Map.of(
                "status", "REJECTED",
                "transactionId", "txn-999"
        );
        ResponseEntity<Map> response = ResponseEntity.ok(responseBody);

        Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(), Mockito.eq(Map.class)))
                .thenReturn(response);

        // Act
        PaymentStatusResponse result = paymentGatewayClient.process(payment);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("REJECTED", result.getStatus());
        Assertions.assertEquals("txn-999", result.getTransactionId());

        Mockito.verify(restTemplate).postForEntity(Mockito.anyString(), Mockito.any(), Mockito.eq(Map.class));
    }

    @Test
    void shouldHandleNullResponseFromGateway() {
        // Arrange
        Payment payment = Payment.builder()
                .id("payment-null")
                .subscriptionId(5L)
                .amount(500.0)
                .status(PaymentStatus.PENDING)
                .method(PaymentMethod.CREDIT_CARD)
                .paymentDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mockito.when(restTemplate.postForEntity(Mockito.anyString(), Mockito.any(), Mockito.eq(Map.class)))
                .thenReturn(null);

        // Act & Assert
        Assertions.assertThrows(NullPointerException.class, () -> {
            paymentGatewayClient.process(payment);
        });

        Mockito.verify(restTemplate).postForEntity(Mockito.anyString(), Mockito.any(), Mockito.eq(Map.class));
    }
} 