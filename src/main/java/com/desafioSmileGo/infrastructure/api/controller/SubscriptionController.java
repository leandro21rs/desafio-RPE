package com.desafioSmileGo.infrastructure.api.controller;

import com.desafioSmileGo.application.usecase.subscription.*;
import com.desafioSmileGo.domain.model.Subscription;
import com.desafioSmileGo.domain.model.enums.Plan;
import com.desafioSmileGo.domain.model.enums.SubscriptionStatus;
import com.desafioSmileGo.infrastructure.api.dto.SubscriptionRequest;
import com.desafioSmileGo.infrastructure.api.dto.SubscriptionResponse;
import com.desafioSmileGo.infrastructure.api.dto.SubscriptionUpdatePlanRequest;
import com.desafioSmileGo.infrastructure.api.dto.SubscriptionsReportResponse;
import com.desafioSmileGo.infrastructure.api.mapper.SubscriptionDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
@Tag(name = "Assinaturas")
public class SubscriptionController {

    private final CreateSubscriptionUseCase createUseCase;
    private final ListSubscriptionsUseCase listUseCase;
    private final CancelSubscriptionUseCase cancelSubscriptionUseCase;
    private final UpdateSubscriptionUseCase updateSubscriptionUseCase;
    private final GetSubscriptionByIdUseCase getSubscriptionByIdUseCase;
    private final SubscriptionsReportUseCase subscriptionsReportUseCase;

    @PostMapping
    @Operation(summary = "Criar nova assinatura")
    public ResponseEntity<SubscriptionResponse> create(@Valid @RequestBody SubscriptionRequest request) {
        var subscription = SubscriptionDtoMapper.toDomain(request);
        var created = createUseCase.execute(subscription);

        return ResponseEntity.status(HttpStatus.CREATED).body(SubscriptionDtoMapper.toResponse(created));
    }

    @GetMapping("/find")
    @Operation(summary = "Listar assinaturas com filtros")
    public ResponseEntity<List<SubscriptionResponse>> list(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String clientId,
            @RequestParam(required = false) Plan plan,
            @RequestParam(required = false) SubscriptionStatus status,
            @RequestParam(required = false) LocalDateTime createdStartDate,
            @RequestParam(required = false) LocalDateTime createdEndDate) {
        return ResponseEntity.ok(
                listUseCase.execute(id, clientId, plan, status, createdStartDate, createdEndDate).stream()
                        .map(SubscriptionDtoMapper::toResponse)
                        .toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar determinada assinatura através do id")
    public ResponseEntity<SubscriptionResponse> findById(@PathVariable Long id) {
        Subscription subscription = getSubscriptionByIdUseCase.execute(id);

        return ResponseEntity.status(HttpStatus.OK).body(SubscriptionDtoMapper.toResponse(subscription));
    }

    @PutMapping("/{id}/plan/update")
    @Operation(summary = "Alterar plano de uma determinada assinatura")
    public ResponseEntity<SubscriptionResponse> updatePlanSubscription(
            @PathVariable Long id,
            @Valid @RequestBody SubscriptionUpdatePlanRequest updateData) {

        Subscription subscriptionUpdate = SubscriptionDtoMapper.toDomain(updateData);
        Subscription subscriptionCancelled = updateSubscriptionUseCase.execute(id, subscriptionUpdate);

        return ResponseEntity.status(HttpStatus.OK).body(SubscriptionDtoMapper.toResponse(subscriptionCancelled));
    }

    @PutMapping("/{id}/cancelled")
    @Operation(summary = "Cancelar uma determinada assinatura")
    public ResponseEntity<SubscriptionResponse> cancelledSubscription(@PathVariable Long id) {
        Subscription subscriptionCancelled = cancelSubscriptionUseCase.execute(id);

        return ResponseEntity.status(HttpStatus.OK).body(SubscriptionDtoMapper.toResponse(subscriptionCancelled));
    }

    @GetMapping("/report")
    @Operation(summary = "Relatório de assinaturas")
    public ResponseEntity<List<SubscriptionsReportResponse>> getSubscriptionsReport(
            @RequestParam(required = false) String clientId,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate) {
        List<SubscriptionsReportResponse> report = subscriptionsReportUseCase.execute(clientId, startDate, endDate);

        return ResponseEntity.status(HttpStatus.OK).body(report);
    }
}
