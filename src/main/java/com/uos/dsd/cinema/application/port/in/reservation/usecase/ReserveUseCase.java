package com.uos.dsd.cinema.application.port.in.reservation.usecase;

import com.uos.dsd.cinema.application.port.in.reservation.command.ReserveCommand;

public interface ReserveUseCase {

    Long reserve(ReserveCommand command);
}
