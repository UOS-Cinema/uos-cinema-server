package com.uos.dsd.cinema.application.port.in.reservation.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record ReservationHistoryResponse(
    Long reservationId,
    ScreeningInfo screening,
    List<String> seatNumbers,
    Map<String, Integer> customerCounts,
    LocalDateTime createdAt,
    String status,
    Long paymentId
) {
    public record ScreeningInfo(
        String movieTitle,
        String theaterName,
        String screenType,
        LocalDateTime startTime,
        Integer duration
    ) {}
} 
