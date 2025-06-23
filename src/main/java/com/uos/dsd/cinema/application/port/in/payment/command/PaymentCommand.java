package com.uos.dsd.cinema.application.port.in.payment.command;

import java.util.Map;

import com.uos.dsd.cinema.domain.payment.PaymentMethod;

public record PaymentCommand(
    Long userId,
    Long reservationId,
    Map<String, Integer> customerCount,
    PaymentMethod paymentMethod,
    String affiliateName,
    int usedPoint
) {

}
