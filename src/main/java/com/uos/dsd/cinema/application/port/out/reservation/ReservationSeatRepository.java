package com.uos.dsd.cinema.application.port.out.reservation;

import com.uos.dsd.cinema.domain.reservation.ReservationSeat;
import com.uos.dsd.cinema.domain.reservation.ReservationSeatId;

import java.util.Optional;

public interface ReservationSeatRepository {

    ReservationSeat save(ReservationSeat reservationSeat);

    Optional<ReservationSeat> findById(ReservationSeatId id);
}
