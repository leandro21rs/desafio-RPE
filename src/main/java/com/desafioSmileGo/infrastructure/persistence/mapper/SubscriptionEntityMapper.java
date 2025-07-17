package com.desafioSmileGo.infrastructure.persistence.mapper;

import com.desafioSmileGo.domain.model.Subscription;
import com.desafioSmileGo.infrastructure.persistence.entity.SubscriptionEntity;

public class SubscriptionEntityMapper {
    public static SubscriptionEntity toEntity(Subscription s) {
        if (s == null) return null;
        return SubscriptionEntity.builder()
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

    public static Subscription toDomain(SubscriptionEntity e) {
        if (e == null) return null;
        return Subscription.builder()
                .id(e.getId())
                .clientId(e.getClientId())
                .plan(e.getPlan())
                .status(e.getStatus())
                .startDate(e.getStartDate())
                .endDate(e.getEndDate())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }
}
