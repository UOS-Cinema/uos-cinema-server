package com.uos.dsd.cinema.application.port.out.payment;

import java.time.LocalDateTime;

public record PaymentResult(

    boolean success,
    String approvalNumber,
    LocalDateTime approvedAt,
    String errorMessage
) {
    
    public static PaymentResult success(String approvalNumber) {

        return new PaymentResult(true, approvalNumber, LocalDateTime.now(), null);
    }
    
    public static PaymentResult failure(String errorMessage) {

        return new PaymentResult(false, null, null, errorMessage);
    }
} 