package com.desafioSmileGo.infrastructure.api.dto;

import java.io.Serializable;

import com.desafioSmileGo.domain.model.enums.PaymentMethod;
import com.desafioSmileGo.domain.model.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentReportResponse implements Serializable {
  private static final long serialVersionUID = 1L;

  private PaymentMethod method;
  private PaymentStatus status;
  private Long count;
  private Double totalAmount;
}
