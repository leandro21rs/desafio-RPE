package com.desafioSmileGo.infrastructure.api.controller;

import com.desafioSmileGo.application.usecase.payment.CreatePaymentUseCase;
import com.desafioSmileGo.application.usecase.payment.GetPaymentByIdUseCase;
import com.desafioSmileGo.application.usecase.payment.ListPaymentsUseCase;
import com.desafioSmileGo.application.usecase.payment.UpdatePaymentStatusUseCase;
import com.desafioSmileGo.domain.model.Payment;
import com.desafioSmileGo.infrastructure.api.dto.PaymentRequest;
import com.desafioSmileGo.infrastructure.api.dto.PaymentResponse;
import com.desafioSmileGo.infrastructure.api.dto.PaymentUpdateStatusRequest;
import com.desafioSmileGo.infrastructure.api.mapper.PaymentDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Tag(name = "Pagamentos")
public class PaymentController {

    private final CreatePaymentUseCase createPaymentUseCase;
    private final ListPaymentsUseCase listPaymentsUseCase;
    private final UpdatePaymentStatusUseCase updatePaymentStatusUseCase;
    private final GetPaymentByIdUseCase getPaymentByIdUseCase;

    @PostMapping
    @Operation(summary = "Cria um novo pagamento")
    public ResponseEntity<PaymentResponse> create(@RequestBody PaymentRequest request) {
        Payment payment = PaymentDtoMapper.toDomain(request);
        payment.setCreatedAt(LocalDateTime.now());
        payment.setPaymentDate(LocalDateTime.now());

        Payment savedPayment = createPaymentUseCase.execute(payment);

        return ResponseEntity.status(HttpStatus.OK).body(PaymentDtoMapper.toResponse(savedPayment));
    }

    @GetMapping
    @Operation(summary = "Busca todos os pagamentos")
    public ResponseEntity<List<PaymentResponse>> findAllPayments() {
        List<Payment> list = listPaymentsUseCase.execute();
        return ResponseEntity.ok(
                list.stream().map(PaymentDtoMapper::toResponse).toList()
        );
    }

    @GetMapping("/find")
    @Operation(summary = "Busca pagamento por id")
    public ResponseEntity<PaymentResponse> findById(@RequestHeader("payment-id") String id) {
        Payment payment = getPaymentByIdUseCase.execute((id));

        return ResponseEntity.status(HttpStatus.OK).body(PaymentDtoMapper.toResponse(payment));
    }

    @PutMapping("/update")
    @Operation(summary = "Atualiza status de um pagamento")
    public ResponseEntity<PaymentResponse> updateStatusPayment(@RequestBody PaymentUpdateStatusRequest updateData) {
        Payment updatedPayment = updatePaymentStatusUseCase.updatePaymentProducer(updateData.getId(), updateData.getStatus());

        return ResponseEntity.status(HttpStatus.OK).body(PaymentDtoMapper.toResponse(updatedPayment));
    }
}
