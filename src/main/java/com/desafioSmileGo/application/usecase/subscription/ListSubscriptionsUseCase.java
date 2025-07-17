package com.desafioSmileGo.application.usecase.subscription;

import com.desafioSmileGo.domain.model.Subscription;
import com.desafioSmileGo.domain.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListSubscriptionsUseCase {

    private final SubscriptionRepository repository;

    public List<Subscription> execute() {
        return repository.findAll();
    }
}
