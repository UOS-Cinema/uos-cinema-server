package com.uos.dsd.cinema.adaptor.in.web.payment.request;

import java.util.Map;

import com.uos.dsd.cinema.application.port.in.payment.command.PaymentCommand;
import com.uos.dsd.cinema.domain.payment.PaymentMethod;

public record PaymentRequest(
    Long reservationId,
    Map<String, Integer> customerCount,
    PaymentMethod paymentMethod,  // 결제 수단 (카드, 계좌이체 등)
    String affiliateName,         // 제휴사명 (삼성카드, 현대카드 등)
    int usedPoint                 // 사용할 포인트 (선택사항)
) {

    public PaymentCommand toCommand(Long userId) {
        return new PaymentCommand(
            userId, 
            reservationId, 
            customerCount, 
            paymentMethod, 
            affiliateName, 
            usedPoint);
    }
}
