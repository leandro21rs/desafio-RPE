package com.desafioSmileGo.infrastructure.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {
    private String id;
    private Long subscriptionId;
    private Double amount;
    private String status;
    private String method;
    private LocalDateTime paymentDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
