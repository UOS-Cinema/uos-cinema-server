package com.uos.dsd.cinema.adaptor.out.persistence.reservation;

import com.uos.dsd.cinema.application.port.out.reservation.ReservationSeatRepository;
import com.uos.dsd.cinema.domain.reservation.ReservationSeat;
import com.uos.dsd.cinema.domain.reservation.ReservationSeatId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationSeatJpaRepository 
        extends JpaRepository<ReservationSeat, ReservationSeatId>, ReservationSeatRepository {

    Optional<ReservationSeat> findById(ReservationSeatId id);
} 
