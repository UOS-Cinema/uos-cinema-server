package com.uos.dsd.cinema.adaptor.out.persistence.reservation;

import com.uos.dsd.cinema.application.port.out.reservation.ReservationRepository;
import com.uos.dsd.cinema.domain.reservation.Reservation;
import com.uos.dsd.cinema.domain.reservation.enums.ReservationStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationJpaRepository 
        extends JpaRepository<Reservation, Long>, ReservationRepository {

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

    @Query("""
        SELECT r FROM Reservation r
        WHERE r.customerId = :customerId
        AND (r.status = com.uos.dsd.cinema.domain.reservation.enums.ReservationStatus.COMPLETED 
             OR r.status = com.uos.dsd.cinema.domain.reservation.enums.ReservationStatus.CANCELED)
        ORDER BY r.createdAt DESC
    """)
    Page<Reservation> findCompletedReservationsByCustomerId(@Param("customerId") Long customerId, Pageable pageable);
} 
