package com.desafioSmileGo.infrastructure.external.payment;

import com.desafioSmileGo.domain.model.Payment;
import com.desafioSmileGo.domain.model.Subscription;
import com.desafioSmileGo.domain.model.enums.PaymentStatus;
import com.desafioSmileGo.exception.RetryTestException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentGatewayClient {

    private final RestTemplate restTemplate;

    @Value("${external.payment-gateway.url}")
    private String gatewayUrl;

    @CircuitBreaker(name = "paymentGateway", fallbackMethod = "fallbackPayment")
    @Retry(name = "paymentGateway")
    public PaymentStatusResponse process(Payment payment) {
         // Remover comentários para teste de retry e circuit breaker
        //  System.out.println("RETRY - Tentando chamar o serviço...");
        //  throw new RetryTestException("Erro simulado para teste do retry");

        String url = gatewayUrl + "/process";

        Map<String, Object> request = Map.of(
                "subscriptionId", payment.getSubscriptionId(),
                "amount", payment.getAmount(),
                "method", payment.getMethod()
        );

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        String status = (String) response.getBody().get("status");
        String transactionId = (String) response.getBody().get("transactionId");

        return new PaymentStatusResponse(status, transactionId);
    }

    public PaymentStatusResponse fallbackPayment(Payment payment, Throwable t) {
        System.out.println("CIRCUIT BREAKER - Caiu no fallback devido a: " + t.getMessage());

        return new PaymentStatusResponse("ERROR", UUID.randomUUID().toString());
    }
}
