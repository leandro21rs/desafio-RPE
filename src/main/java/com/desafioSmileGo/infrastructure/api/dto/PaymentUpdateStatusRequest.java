package com.desafioSmileGo.infrastructure.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PaymentUpdateStatusRequest {
    @NotBlank
    private String id;

    @NotBlank
    private String status;
}
