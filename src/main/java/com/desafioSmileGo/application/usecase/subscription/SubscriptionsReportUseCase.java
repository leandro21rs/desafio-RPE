package com.desafioSmileGo.application.usecase.subscription;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.desafioSmileGo.domain.repository.SubscriptionRepository;
import com.desafioSmileGo.infrastructure.api.dto.SubscriptionsReportResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscriptionsReportUseCase {

  private final SubscriptionRepository repository;

  public List<SubscriptionsReportResponse> execute(String clientId, LocalDateTime startDate, LocalDateTime endDate) {
    return repository.getSubscriptionsReport(clientId, startDate, endDate);
  }
  
}
