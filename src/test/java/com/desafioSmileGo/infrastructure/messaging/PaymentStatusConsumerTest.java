package com.desafioSmileGo.infrastructure.messaging;

import com.desafioSmileGo.application.usecase.payment.UpdatePaymentStatusUseCase;
import com.desafioSmileGo.domain.model.enums.PaymentStatus;
import com.desafioSmileGo.infrastructure.api.dto.PaymentStatusEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class PaymentStatusConsumerTest {

    @Mock
    private UpdatePaymentStatusUseCase updatePaymentStatusUseCase;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private PaymentStatusConsumer paymentStatusConsumer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReceivePaymentStatusEventWithSuccess() throws Exception {
        // Arrange
        PaymentStatusEvent event = new PaymentStatusEvent();
        event.setPaymentId("payment-123");
        event.setStatus("APPROVED");

        String jsonMessage = "{\"paymentId\":\"payment-123\",\"status\":\"APPROVED\"}";

        Mockito.when(objectMapper.readValue(jsonMessage, PaymentStatusEvent.class)).thenReturn(event);

        // Act
        paymentStatusConsumer.receive(jsonMessage);

        // Assert
        Mockito.verify(objectMapper).readValue(jsonMessage, PaymentStatusEvent.class);
        Mockito.verify(updatePaymentStatusUseCase).execute("payment-123", PaymentStatus.APPROVED);
    }

    @Test
    void shouldReceivePaymentStatusEventWithDeclinedStatus() throws Exception {
        // Arrange
        PaymentStatusEvent event = new PaymentStatusEvent();
        event.setPaymentId("payment-456");
        event.setStatus("DECLINED");

        String jsonMessage = "{\"paymentId\":\"payment-456\",\"status\":\"DECLINED\"}";

        Mockito.when(objectMapper.readValue(jsonMessage, PaymentStatusEvent.class)).thenReturn(event);

        // Act
        paymentStatusConsumer.receive(jsonMessage);

        // Assert
        Mockito.verify(objectMapper).readValue(jsonMessage, PaymentStatusEvent.class);
        Mockito.verify(updatePaymentStatusUseCase).execute("payment-456", PaymentStatus.DECLINED);
    }

    @Test
    void shouldReceivePaymentStatusEventWithErrorStatus() throws Exception {
        // Arrange
        PaymentStatusEvent event = new PaymentStatusEvent();
        event.setPaymentId("payment-789");
        event.setStatus("ERROR");

        String jsonMessage = "{\"paymentId\":\"payment-789\",\"status\":\"ERROR\"}";

        Mockito.when(objectMapper.readValue(jsonMessage, PaymentStatusEvent.class)).thenReturn(event);

        // Act
        paymentStatusConsumer.receive(jsonMessage);

        // Assert
        Mockito.verify(objectMapper).readValue(jsonMessage, PaymentStatusEvent.class);
        Mockito.verify(updatePaymentStatusUseCase).execute("payment-789", PaymentStatus.ERROR);
    }

    @Test
    void shouldReceivePaymentStatusEventWithRejectedStatus() throws Exception {
        // Arrange
        PaymentStatusEvent event = new PaymentStatusEvent();
        event.setPaymentId("payment-999");
        event.setStatus("REJECTED");

        String jsonMessage = "{\"paymentId\":\"payment-999\",\"status\":\"REJECTED\"}";

        Mockito.when(objectMapper.readValue(jsonMessage, PaymentStatusEvent.class)).thenReturn(event);

        // Act
        paymentStatusConsumer.receive(jsonMessage);

        // Assert
        Mockito.verify(objectMapper).readValue(jsonMessage, PaymentStatusEvent.class);
        Mockito.verify(updatePaymentStatusUseCase).execute("payment-999", PaymentStatus.REJECTED);
    }

    @Test
    void shouldHandleExceptionWhenJsonIsInvalid() throws Exception {
        // Arrange
        String invalidJsonMessage = "invalid json";

        Mockito.when(objectMapper.readValue(invalidJsonMessage, PaymentStatusEvent.class))
                .thenThrow(new RuntimeException("Invalid JSON"));

        // Act
        paymentStatusConsumer.receive(invalidJsonMessage);

        // Assert
        Mockito.verify(objectMapper).readValue(invalidJsonMessage, PaymentStatusEvent.class);
        Mockito.verify(updatePaymentStatusUseCase, Mockito.never()).execute(Mockito.anyString(), Mockito.any(PaymentStatus.class));
    }

    @Test
    void shouldHandleExceptionWhenPaymentStatusIsInvalid() throws Exception {
        // Arrange
        PaymentStatusEvent event = new PaymentStatusEvent();
        event.setPaymentId("payment-123");
        event.setStatus("INVALID_STATUS");

        String jsonMessage = "{\"paymentId\":\"payment-123\",\"status\":\"INVALID_STATUS\"}";

        Mockito.when(objectMapper.readValue(jsonMessage, PaymentStatusEvent.class)).thenReturn(event);

        // Act
        paymentStatusConsumer.receive(jsonMessage);

        // Assert
        Mockito.verify(objectMapper).readValue(jsonMessage, PaymentStatusEvent.class);
        Mockito.verify(updatePaymentStatusUseCase, Mockito.never()).execute(Mockito.anyString(), Mockito.any(PaymentStatus.class));
    }
} 