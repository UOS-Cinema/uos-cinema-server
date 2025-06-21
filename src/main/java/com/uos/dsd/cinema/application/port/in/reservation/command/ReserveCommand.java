package com.uos.dsd.cinema.application.port.in.reservation.command;

import java.util.List;
import java.util.Map;

public record ReserveCommand(
    Long customerId,
    Long screeningId,
    Long theaterId,
    List<String> seatNumbers,
    Map<String, String> customerCount
) {
}
