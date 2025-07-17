package com.desafioSmileGo.infrastructure.messaging;

import com.desafioSmileGo.infrastructure.api.dto.PaymentStatusEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStatusProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Value("${queue.payment-status}")
    private String queueName;

    public void send(PaymentStatusEvent event) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(event);
            rabbitTemplate.convertAndSend(queueName, jsonMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
