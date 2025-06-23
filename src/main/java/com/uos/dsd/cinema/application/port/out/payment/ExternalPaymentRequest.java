package com.uos.dsd.cinema.application.port.out.payment;

import com.uos.dsd.cinema.domain.payment.PaymentMethod;

public record ExternalPaymentRequest(
    int price,
    String affiliateName,
    PaymentMethod paymentMethod
) {
    
}
