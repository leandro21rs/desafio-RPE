package com.desafioSmileGo.application.usecase.subscription;

import com.desafioSmileGo.domain.model.Subscription;
import com.desafioSmileGo.domain.model.enums.SubscriptionStatus;
import com.desafioSmileGo.domain.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CreateSubscriptionUseCase {

    private final SubscriptionRepository repository;

    public Subscription execute(Subscription subscription) {
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        subscription.setCreatedAt(LocalDateTime.now());
        subscription.setStartDate(LocalDateTime.now());
        subscription.setUpdatedAt(LocalDateTime.now());
        return repository.save(subscription);
    }
}
