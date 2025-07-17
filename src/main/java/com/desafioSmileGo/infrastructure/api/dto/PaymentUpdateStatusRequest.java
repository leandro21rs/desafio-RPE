package com.desafioSmileGo.infrastructure.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PaymentUpdateStatusRequest {
    @NotBlank
    private String id;

    @NotBlank
    private String status;
}
