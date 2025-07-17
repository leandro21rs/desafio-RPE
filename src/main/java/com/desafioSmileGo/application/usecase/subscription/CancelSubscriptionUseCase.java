package com.desafioSmileGo.application.usecase.subscription;

import com.desafioSmileGo.domain.model.Subscription;
import com.desafioSmileGo.domain.model.enums.SubscriptionStatus;
import com.desafioSmileGo.domain.repository.SubscriptionRepository;
import com.desafioSmileGo.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CancelSubscriptionUseCase {

    private final SubscriptionRepository repository;

    public Subscription execute(Long id) {
        Subscription existing = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subscription not found: " + id));

        existing.setStatus(SubscriptionStatus.CANCELLED);
        existing.setUpdatedAt(LocalDateTime.now());
        existing.setEndDate(LocalDateTime.now());

        return repository.save(existing);
    }
}
