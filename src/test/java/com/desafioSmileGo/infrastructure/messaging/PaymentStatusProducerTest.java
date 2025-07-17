package com.desafioSmileGo.infrastructure.messaging;

import com.desafioSmileGo.infrastructure.api.dto.PaymentStatusEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.mockito.ArgumentCaptor;

class PaymentStatusProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private PaymentStatusProducer paymentStatusProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSendPaymentStatusEventWithSuccess() throws Exception {
        // Arrange
        PaymentStatusEvent event = new PaymentStatusEvent();
        event.setPaymentId("payment-123");
        event.setStatus("APPROVED");
        
        String jsonMessage = "{\"paymentId\":\"payment-123\",\"status\":\"APPROVED\"}";
        Mockito.when(objectMapper.writeValueAsString(event)).thenReturn(jsonMessage);

        // Act
        paymentStatusProducer.send(event);

        // Assert
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(rabbitTemplate).convertAndSend(Mockito.isNull(), captor.capture());
        Assertions.assertEquals(jsonMessage, captor.getValue());
    }

    @Test
    void shouldSendPaymentStatusEventWithDeclinedStatus() throws Exception {
        // Arrange
        PaymentStatusEvent event = new PaymentStatusEvent();
        event.setPaymentId("payment-456");
        event.setStatus("DECLINED");
        
        String jsonMessage = "{\"paymentId\":\"payment-456\",\"status\":\"DECLINED\"}";
        Mockito.when(objectMapper.writeValueAsString(event)).thenReturn(jsonMessage);

        // Act
        paymentStatusProducer.send(event);

        // Assert
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(rabbitTemplate).convertAndSend(Mockito.isNull(), captor.capture());
        Assertions.assertEquals(jsonMessage, captor.getValue());
    }

    @Test
    void shouldSendPaymentStatusEventWithErrorStatus() throws Exception {
        // Arrange
        PaymentStatusEvent event = new PaymentStatusEvent();
        event.setPaymentId("payment-789");
        event.setStatus("ERROR");
        
        String jsonMessage = "{\"paymentId\":\"payment-789\",\"status\":\"ERROR\"}";
        Mockito.when(objectMapper.writeValueAsString(event)).thenReturn(jsonMessage);

        // Act
        paymentStatusProducer.send(event);

        // Assert
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(rabbitTemplate).convertAndSend(Mockito.isNull(), captor.capture());
        Assertions.assertEquals(jsonMessage, captor.getValue());
    }

    @Test
    void shouldSendPaymentStatusEventWithRejectedStatus() throws Exception {
        // Arrange
        PaymentStatusEvent event = new PaymentStatusEvent();
        event.setPaymentId("payment-999");
        event.setStatus("REJECTED");
        
        String jsonMessage = "{\"paymentId\":\"payment-999\",\"status\":\"REJECTED\"}";
        Mockito.when(objectMapper.writeValueAsString(event)).thenReturn(jsonMessage);

        // Act
        paymentStatusProducer.send(event);

        // Assert
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(rabbitTemplate).convertAndSend(Mockito.isNull(), captor.capture());
        Assertions.assertEquals(jsonMessage, captor.getValue());
    }

    @Test
    void shouldSendPaymentStatusEventWithPendingStatus() throws Exception {
        // Arrange
        PaymentStatusEvent event = new PaymentStatusEvent();
        event.setPaymentId("payment-111");
        event.setStatus("PENDING");
        
        String jsonMessage = "{\"paymentId\":\"payment-111\",\"status\":\"PENDING\"}";
        Mockito.when(objectMapper.writeValueAsString(event)).thenReturn(jsonMessage);

        // Act
        paymentStatusProducer.send(event);

        // Assert
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(rabbitTemplate).convertAndSend(Mockito.isNull(), captor.capture());
        Assertions.assertEquals(jsonMessage, captor.getValue());
    }

    @Test
    void shouldSendMultiplePaymentStatusEvents() throws Exception {
        // Arrange
        PaymentStatusEvent event1 = new PaymentStatusEvent();
        event1.setPaymentId("payment-1");
        event1.setStatus("APPROVED");

        PaymentStatusEvent event2 = new PaymentStatusEvent();
        event2.setPaymentId("payment-2");
        event2.setStatus("DECLINED");

        String jsonMessage1 = "{\"paymentId\":\"payment-1\",\"status\":\"APPROVED\"}";
        String jsonMessage2 = "{\"paymentId\":\"payment-2\",\"status\":\"DECLINED\"}";
        
        Mockito.when(objectMapper.writeValueAsString(event1)).thenReturn(jsonMessage1);
        Mockito.when(objectMapper.writeValueAsString(event2)).thenReturn(jsonMessage2);

        // Act
        paymentStatusProducer.send(event1);
        paymentStatusProducer.send(event2);

        // Assert
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(rabbitTemplate, Mockito.times(2)).convertAndSend(Mockito.isNull(), captor.capture());
        Assertions.assertEquals(jsonMessage1, captor.getAllValues().get(0));
        Assertions.assertEquals(jsonMessage2, captor.getAllValues().get(1));
    }
} 