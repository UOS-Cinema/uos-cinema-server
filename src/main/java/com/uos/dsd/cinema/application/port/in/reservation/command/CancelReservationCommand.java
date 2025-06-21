package com.uos.dsd.cinema.application.port.in.reservation.command;

public record CancelReservationCommand(
    Long id,
    Long customerId
) {
}
