package com.desafioSmileGo.infrastructure.messaging;

import com.desafioSmileGo.application.usecase.payment.UpdatePaymentStatusUseCase;
import com.desafioSmileGo.domain.model.enums.PaymentStatus;
import com.desafioSmileGo.infrastructure.api.dto.PaymentStatusEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStatusConsumer {

    private final UpdatePaymentStatusUseCase updatePaymentStatusUseCase;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "payment-status-queue")
    public void receive(String jsonMessage) {
        try {
            PaymentStatusEvent paymentStatusEvent = objectMapper.readValue(jsonMessage, PaymentStatusEvent.class);
            System.out.println(("CONSUMER DATA " + paymentStatusEvent));

            updatePaymentStatusUseCase.execute(paymentStatusEvent.getPaymentId(), PaymentStatus.valueOf(paymentStatusEvent.getStatus()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
