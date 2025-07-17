package com.desafioSmileGo.infrastructure.api.dto;

import com.desafioSmileGo.domain.model.enums.Plan;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SubscriptionUpdatePlanRequest {
    @NotBlank
    private Plan plan;
}
