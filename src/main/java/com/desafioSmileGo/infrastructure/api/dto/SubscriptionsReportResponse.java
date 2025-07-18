package com.desafioSmileGo.infrastructure.api.dto;

import java.io.Serializable;

import com.desafioSmileGo.domain.model.enums.Plan;
import com.desafioSmileGo.domain.model.enums.SubscriptionStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubscriptionsReportResponse implements Serializable {
  private static final long serialVersionUID = 1L;

  private Plan plan;
  private SubscriptionStatus status;
  private Long count;
}
