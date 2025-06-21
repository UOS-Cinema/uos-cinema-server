package com.uos.dsd.cinema.adaptor.in.web.reservation.request;

import java.util.List;
import java.util.Map;

import com.uos.dsd.cinema.application.port.in.reservation.command.ReserveCommand;

public record ReserveRequest(
    Long screeningId,
    Long theaterId,
    List<String> seatNumbers,
    Map<String, String> customerCount
) {

    public ReserveCommand toCommand(Long userId) {
        return new ReserveCommand(
            userId,
            screeningId,
            theaterId,
            seatNumbers,
            customerCount
        );
    }
}
