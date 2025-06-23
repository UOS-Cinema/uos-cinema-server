package com.uos.dsd.cinema.application.port.out.reservation;

import com.uos.dsd.cinema.domain.reservation.Reservation;
import com.uos.dsd.cinema.domain.reservation.enums.ReservationStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    Reservation saveAndFlush(Reservation reservation);

    Optional<Reservation> findById(Long id);

    List<Long> findExpiredReservationIds(ReservationStatus reservedStatus, LocalDateTime threshold);

    int cancelReservationsByIds(ReservationStatus canceledStatus, List<Long> ids);

    void deleteSeatsByReservationIds(List<Long> reservationIds);

    Page<Reservation> findCompletedReservationsByCustomerId(Long customerId, Pageable pageable);
}
