package com.desafioSmileGo.infrastructure.api.mapper;

import com.desafioSmileGo.domain.model.Subscription;
import com.desafioSmileGo.infrastructure.api.dto.SubscriptionRequest;
import com.desafioSmileGo.infrastructure.api.dto.SubscriptionResponse;
import com.desafioSmileGo.infrastructure.api.dto.SubscriptionUpdatePlanRequest;

public class SubscriptionDtoMapper {
    public static Subscription toDomain(SubscriptionRequest req) {
        return Subscription.builder()
                .clientId(req.getClientId())
                .plan(req.getPlan())
                .endDate(req.getEndDate())
                .build();
    }

    public static Subscription toDomain(SubscriptionUpdatePlanRequest updateData) {
        return Subscription.builder()
                .plan(updateData.getPlan())
                .build();
    }

    public static SubscriptionResponse toResponse(Subscription s) {
        return SubscriptionResponse.builder()
                .id(s.getId())
                .clientId(s.getClientId())
                .plan(s.getPlan())
                .status(s.getStatus())
                .startDate(s.getStartDate())
                .endDate(s.getEndDate())
                .createdAt(s.getCreatedAt())
                .updatedAt(s.getUpdatedAt())
                .build();
    }
}
