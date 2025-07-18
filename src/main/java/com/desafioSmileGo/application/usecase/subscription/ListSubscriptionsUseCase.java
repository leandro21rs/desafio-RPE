package com.desafioSmileGo.application.usecase.subscription;

import com.desafioSmileGo.domain.model.Subscription;
import com.desafioSmileGo.domain.model.enums.Plan;
import com.desafioSmileGo.domain.model.enums.SubscriptionStatus;
import com.desafioSmileGo.domain.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ListSubscriptionsUseCase {

    private final SubscriptionRepository repository;

    public List<Subscription> execute(Long id, String clientId, Plan plan, SubscriptionStatus status, LocalDateTime createdStartDate, LocalDateTime createdEndDate) {
        return repository.findByFilters(id, clientId, plan, status, createdStartDate, createdEndDate);
    }
}
