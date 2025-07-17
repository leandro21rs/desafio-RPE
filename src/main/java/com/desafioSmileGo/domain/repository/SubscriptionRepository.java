package com.desafioSmileGo.domain.repository;

import com.desafioSmileGo.domain.model.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository {
    Subscription save(Subscription subscription);

    Optional<Subscription> findById(Long id);

    List<Subscription> findAll();

    void deleteById(Long id);
}
