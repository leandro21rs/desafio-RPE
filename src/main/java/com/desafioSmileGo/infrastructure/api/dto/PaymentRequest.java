package com.desafioSmileGo.infrastructure.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PaymentRequest {
    @NotBlank
    private Long subscriptionId;

    @NotNull
    private Double amount;

    @NotNull
    private String method;
}
