package com.desafioSmileGo.application.usecase.subscription;

import com.desafioSmileGo.domain.model.Subscription;
import com.desafioSmileGo.domain.repository.SubscriptionRepository;
import com.desafioSmileGo.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UpdateSubscriptionUseCase {

    private final SubscriptionRepository repository;

    public Subscription execute(Long id, Subscription updatedData) {
        Subscription subscription = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subscription not found: " + id));

        subscription.setPlan(updatedData.getPlan());
        subscription.setUpdatedAt(LocalDateTime.now());

        return repository.save(subscription);
    }
}
