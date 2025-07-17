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

import java.util.Optional;

class GetPaymentByIdUseCaseTest {
    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private GetPaymentByIdUseCase getPaymentByIdUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void GetPaymentByIdWithSucces() {
        Payment paymentMock = Payment.builder()
                .id("UUID")
                .subscriptionId(1L)
                .status(PaymentStatus.PENDING)
                .build();

        Mockito.when(paymentRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(paymentMock));

        Payment payment = getPaymentByIdUseCase.execute("UUID");

        Assertions.assertNotNull(payment);
        Assertions.assertEquals(payment.getId(), paymentMock.getId());

        Mockito.verify(paymentRepository).findById(Mockito.any());
    }
}
