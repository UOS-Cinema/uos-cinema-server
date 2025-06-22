package com.uos.dsd.cinema.application.port.in.reservation.usecase;

import com.uos.dsd.cinema.application.port.in.reservation.command.CancelReservationCommand;

public interface CancelReservationUseCase {

    void cancel(CancelReservationCommand command);
}
