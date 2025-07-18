package com.desafioSmileGo.application.usecase.payment;

import com.desafioSmileGo.domain.model.Payment;
import com.desafioSmileGo.domain.model.enums.PaymentStatus;
import com.desafioSmileGo.domain.repository.PaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

class ListPaymentsUseCaseTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private ListPaymentsUseCase listPaymentsUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getListPaymentWithFiltersSuccess() {
        Payment paymentMock1 = Payment.builder()
                .id("UUID-1")
                .subscriptionId(1L)
                .status(PaymentStatus.APPROVED)
                .build();

        Payment paymentMock2 = Payment.builder()
                .id("UUID-2")
                .subscriptionId(2L)
                .status(PaymentStatus.PENDING)
                .build();

        Mockito.when(paymentRepository.findByFilters(
                Mockito.eq(null), 
                Mockito.eq(null), 
                Mockito.eq(PaymentStatus.PENDING), 
                Mockito.eq(null), 
                Mockito.eq(null), 
                Mockito.eq(null) 
        )).thenReturn(List.of(paymentMock1, paymentMock2));

        List<Payment> payments = listPaymentsUseCase.execute(
                null, null, PaymentStatus.PENDING, null, null, null
        );

        Assertions.assertNotNull(payments);
        Assertions.assertEquals(2, payments.size());
    }
}
