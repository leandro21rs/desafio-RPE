package com.desafioSmileGo.infrastructure.api.controller.mock;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/mock/payment-gateway")
public class MockPaymentGatewayController {

    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> processPayment(@RequestBody Map<String, Object> payment) {
        // Simula tempo de resposta de resultado aleatóorio
        try {
            Thread.sleep(1000); // simula latência
        } catch (InterruptedException ignored) {}

        boolean approved = new Random().nextBoolean();

        return ResponseEntity.ok(Map.of(
                "status", approved ? "APPROVED" : "REJECTED",
                "transactionId", UUID.randomUUID().toString()
        ));
    }
}
