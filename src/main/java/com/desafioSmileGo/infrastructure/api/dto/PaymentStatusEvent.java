package com.desafioSmileGo.infrastructure.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStatusEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private String paymentId;
    private String status;
}
