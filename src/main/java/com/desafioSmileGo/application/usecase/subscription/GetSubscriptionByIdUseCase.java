package com.desafioSmileGo.application.usecase.subscription;

import com.desafioSmileGo.domain.model.Subscription;
import com.desafioSmileGo.domain.repository.SubscriptionRepository;
import com.desafioSmileGo.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetSubscriptionByIdUseCase {

    private final SubscriptionRepository repository;

    public Subscription execute(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subscription not found: " + id));
    }
}
