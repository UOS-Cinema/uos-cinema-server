package com.uos.dsd.cinema.application.port.in.point.response;

public record PointHistoryResponse(
    Long paymentId,
    Integer point,
    Integer totalPoint
) {
} 
