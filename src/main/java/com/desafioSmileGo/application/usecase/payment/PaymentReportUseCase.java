package com.desafioSmileGo.application.usecase.payment;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.desafioSmileGo.domain.repository.PaymentRepository;
import com.desafioSmileGo.infrastructure.api.dto.PaymentReportResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PaymentReportUseCase {

  private final PaymentRepository repository;

  public List<PaymentReportResponse> execute(Long subscriptionId, LocalDateTime startDate, LocalDateTime endDate) {
    return repository.getPaymentReport(subscriptionId, startDate, endDate);
  }
}
