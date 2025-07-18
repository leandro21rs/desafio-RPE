package com.desafioSmileGo.infrastructure.persistence.repository;

import com.desafioSmileGo.domain.model.enums.Plan;
import com.desafioSmileGo.domain.model.enums.SubscriptionStatus;
import com.desafioSmileGo.infrastructure.persistence.entity.SubscriptionEntity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaSubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

  @Query("SELECT s FROM SubscriptionEntity s " +
      "WHERE (:id IS NULL OR s.id = :id) " +
      "AND (:clientId IS NULL OR s.clientId = :clientId) " +
      "AND (:plan IS NULL OR s.plan = :plan) " +
      "AND (:status IS NULL OR s.status = :status) " +
      "AND (:createdStartDate IS NULL OR s.createdAt >= :createdStartDate) " +
      "AND (:createdEndDate IS NULL OR s.createdAt <= :createdEndDate)")
  List<SubscriptionEntity> findByFilters(
      @Param("id") Long id,
      @Param("clientId") String clientId,
      @Param("plan") Plan plan,
      @Param("status") SubscriptionStatus status,
      @Param("createdStartDate") LocalDateTime createdStartDate,
      @Param("createdEndDate") LocalDateTime createdEndDate
  );
}
