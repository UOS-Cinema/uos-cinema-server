package com.uos.dsd.cinema.application.port.out.payment;

public interface ExternalPaymentGateway {
    
    /**
     * 외부 결제 게이트웨이에 결제 요청
     */
    PaymentResult processPayment(ExternalPaymentRequest externalPaymentRequest);
    
    /**
     * 결제 취소 요청
     */
    PaymentResult cancelPayment(String approvalNumber);
} 