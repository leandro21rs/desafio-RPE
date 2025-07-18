package com.desafioSmileGo.domain.repository;

import com.desafioSmileGo.domain.model.Subscription;
import com.desafioSmileGo.domain.model.enums.Plan;
import com.desafioSmileGo.domain.model.enums.SubscriptionStatus;
import com.desafioSmileGo.infrastructure.api.dto.SubscriptionsReportResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository {
    Subscription save(Subscription subscription);

    Optional<Subscription> findById(Long id);

    List<Subscription> findByFilters(Long id, String clientId, Plan plan, SubscriptionStatus status, LocalDateTime createdStartDate, LocalDateTime createdEndDate);

    void deleteById(Long id);

    List<SubscriptionsReportResponse> getSubscriptionsReport(String clientId, LocalDateTime startDate, LocalDateTime endDate);
}
