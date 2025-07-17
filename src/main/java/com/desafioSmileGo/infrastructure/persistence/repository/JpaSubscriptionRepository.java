package com.desafioSmileGo.infrastructure.persistence.repository;

import com.desafioSmileGo.infrastructure.persistence.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {
}
