package com.uos.dsd.cinema.application.port.in.payment.response;

import java.time.LocalDateTime;

public record PaymentHistoryResponse(
    Long reservationId,
    String movieTitle,
    Long paymentId,
    Integer originalPrice,
    Integer discountAmount,
    Integer usedPoint,
    Integer earnedPoint,
    Integer finalPrice,
    String paymentMethod,
    LocalDateTime paymentDate
) {
} 
