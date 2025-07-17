package com.desafioSmileGo.infrastructure.external.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStatusResponse {
    private String status;
    private String transactionId;
}