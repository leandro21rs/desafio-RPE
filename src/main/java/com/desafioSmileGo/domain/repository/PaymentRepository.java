package com.desafioSmileGo.domain.repository;

import com.desafioSmileGo.domain.model.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {
    Payment save(Payment payment);

    Optional<Payment> findById(String id);

    List<Payment> findAll();
}
