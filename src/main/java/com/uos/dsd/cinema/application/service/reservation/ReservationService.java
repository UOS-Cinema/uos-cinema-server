package com.uos.dsd.cinema.application.service.reservation;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.uos.dsd.cinema.application.port.in.reservation.usecase.CancelReservationUseCase;
import com.uos.dsd.cinema.application.port.in.reservation.usecase.ReserveUseCase;
import com.uos.dsd.cinema.application.port.in.reservation.command.CancelReservationCommand;
import com.uos.dsd.cinema.application.port.in.reservation.command.ReserveCommand;

@Service
@RequiredArgsConstructor
public class ReservationService implements ReserveUseCase, CancelReservationUseCase {

    @Override
    public Long reserve(ReserveCommand command) {
        return null;
    }

    @Override
    public void cancel(CancelReservationCommand command) {
    }
}
