package com.uos.dsd.cinema.application.port.out.reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uos.dsd.cinema.domain.reservation.Reservation;
import com.uos.dsd.cinema.domain.reservation.enums.ReservationStatus;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

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
}
