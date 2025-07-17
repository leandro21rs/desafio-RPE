package com.desafioSmileGo.infrastructure.persistence.adapter;

import com.desafioSmileGo.domain.model.Payment;
import com.desafioSmileGo.domain.repository.PaymentRepository;
import com.desafioSmileGo.infrastructure.persistence.entity.PaymentEntity;
import com.desafioSmileGo.infrastructure.persistence.mapper.PaymentEntityMapper;
import com.desafioSmileGo.infrastructure.persistence.repository.JpaPaymentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final JpaPaymentRepository jpaRepository;

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = PaymentEntityMapper.toEntity(payment);
        return PaymentEntityMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Payment> findById(String id) {
        return jpaRepository.findById(id).map(PaymentEntityMapper::toDomain);
    }

    @Override
    @Cacheable(value = "payments", key = "'allPayments'")
    public List<Payment> findAll() {
        return jpaRepository.findAll().stream()
                .map(PaymentEntityMapper::toDomain)
                .toList();
    }
}
