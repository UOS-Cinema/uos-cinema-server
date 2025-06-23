package com.uos.dsd.cinema.application.port.in.reservation.command;

import java.util.List;
import java.util.Map;

import com.uos.dsd.cinema.domain.reservation.Reservation;
import com.uos.dsd.cinema.domain.screening.Screening;

public record ReserveCommand(
    Long customerId,
    Long screeningId,
    Long theaterId,
    List<String> seatNumbers,
    Map<String, Integer> customerCount
) {

    public Reservation toReservation(Screening screening) {
        return new Reservation(
            customerId,
            screening,
            theaterId,
            seatNumbers,
            customerCount
        );
    }
}
