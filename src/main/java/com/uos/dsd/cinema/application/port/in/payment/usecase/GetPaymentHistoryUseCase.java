package com.uos.dsd.cinema.application.port.in.payment.usecase;

import com.uos.dsd.cinema.application.port.in.payment.response.PaymentHistoryResponse;

import org.springframework.data.domain.Page;

public interface GetPaymentHistoryUseCase {
    
    Page<PaymentHistoryResponse> getPaymentHistory(Long customerId, int page, int size);
} 
