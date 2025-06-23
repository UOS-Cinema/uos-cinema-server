package com.uos.dsd.cinema.application.port.out.reservation;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uos.dsd.cinema.domain.reservation.ReservationSeat;
import com.uos.dsd.cinema.domain.reservation.ReservationSeatId;

public interface ReservationSeatRepository extends JpaRepository<ReservationSeat, ReservationSeatId> {

    Optional<ReservationSeat> findById(ReservationSeatId id);
}
