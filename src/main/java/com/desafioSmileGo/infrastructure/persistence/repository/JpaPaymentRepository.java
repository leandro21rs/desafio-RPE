package com.desafioSmileGo.infrastructure.persistence.repository;

import com.desafioSmileGo.infrastructure.persistence.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPaymentRepository extends JpaRepository<PaymentEntity, String> {
}
