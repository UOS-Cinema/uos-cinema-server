package com.uos.dsd.cinema.application.port.out.reservation;

import com.uos.dsd.cinema.domain.reservation.Reservation;
import com.uos.dsd.cinema.domain.reservation.enums.ReservationStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    Reservation saveAndFlush(Reservation reservation);

    Optional<Reservation> findById(Long id);

    @Query("SELECT r.id FROM Reservation r WHERE r.status = :reservedStatus AND r.createdAt <= :threshold")
    List<Long> findExpiredReservationIds(
        @Param("reservedStatus") ReservationStatus reservedStatus,
        @Param("threshold") LocalDateTime threshold
    );

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Reservation r SET r.status = :canceledStatus WHERE r.id IN :ids")
    int cancelReservationsByIds(
        @Param("canceledStatus") ReservationStatus canceledStatus,
        @Param("ids") List<Long> ids
    );

    @Modifying
    @Query("DELETE FROM ReservationSeat rs WHERE rs.reservation.id IN :ids")
    void deleteSeatsByReservationIds(@Param("ids") List<Long> reservationIds);

    @Query("SELECT r FROM Reservation r LEFT JOIN FETCH r.screening WHERE r.id = :id")
    Optional<Reservation> findByIdWithScreening(@Param("id") Long id);

    @Query("SELECT r FROM Reservation r WHERE r.customerId = :customerId AND (r.status = 'COMPLETED' OR r.status = 'CANCELED')")
    Page<Reservation> findCompletedReservationsByCustomerId(@Param("customerId") Long customerId, PageRequest pageRequest);
}
