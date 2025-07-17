package com.desafioSmileGo.infrastructure.api.dto;

import com.desafioSmileGo.domain.model.enums.Plan;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class SubscriptionRequest {
    @NotBlank
    private String clientId;

    @NotNull
    private Plan plan;

    private LocalDateTime endDate;
}
