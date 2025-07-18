package com.desafioSmileGo.infrastructure.persistence.repository;

import com.desafioSmileGo.domain.model.enums.PaymentMethod;
import com.desafioSmileGo.domain.model.enums.PaymentStatus;
import com.desafioSmileGo.infrastructure.persistence.entity.PaymentEntity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaPaymentRepository extends JpaRepository<PaymentEntity, String> {


    @Query("SELECT p FROM PaymentEntity p " +
       "WHERE (:id IS NULL OR p.id = :id) " +
       "AND (:subscriptionId IS NULL OR p.subscriptionId = :subscriptionId) " +
       "AND (:status IS NULL OR p.status = :status) " +
       "AND (:method IS NULL OR p.method = :method) " +
       "AND (:startDate IS NULL OR p.paymentDate >= :startDate) " +
       "AND (:endDate IS NULL OR p.paymentDate <= :endDate)")
    List<PaymentEntity> findByFilters(
        @Param("id") String id,
        @Param("subscriptionId") Long subscriptionId,
        @Param("status") PaymentStatus status,
        @Param("method") PaymentMethod method,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
}
