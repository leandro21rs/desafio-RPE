package com.desafioSmileGo.infrastructure.persistence.adapter;

import com.desafioSmileGo.domain.model.Subscription;
import com.desafioSmileGo.domain.model.enums.Plan;
import com.desafioSmileGo.domain.model.enums.SubscriptionStatus;
import com.desafioSmileGo.domain.repository.SubscriptionRepository;
import com.desafioSmileGo.infrastructure.persistence.entity.SubscriptionEntity;
import com.desafioSmileGo.infrastructure.persistence.mapper.SubscriptionEntityMapper;
import com.desafioSmileGo.infrastructure.persistence.repository.JpaSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    private final JpaSubscriptionRepository jpaRepository;

    @Override
    public Subscription save(Subscription subscription) {
        SubscriptionEntity entity = SubscriptionEntityMapper.toEntity(subscription);
        return SubscriptionEntityMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Subscription> findById(Long id) {
        return jpaRepository.findById(id).map(SubscriptionEntityMapper::toDomain);
    }

    @Override
    @Cacheable(value = "subscriptions", key = "'allSubscriptions'")
    public List<Subscription> findByFilters(Long id, String clientId, Plan plan, SubscriptionStatus status, LocalDateTime createdStartDate, LocalDateTime createdEndDate) {
        return jpaRepository.findByFilters(id, clientId, plan, status, createdStartDate, createdEndDate).stream()
                .map(SubscriptionEntityMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
