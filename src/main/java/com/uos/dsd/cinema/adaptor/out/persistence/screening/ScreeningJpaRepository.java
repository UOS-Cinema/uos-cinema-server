package com.uos.dsd.cinema.adaptor.out.persistence.screening;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uos.dsd.cinema.domain.screening.Screening;
import com.uos.dsd.cinema.domain.reservation.ReservationSeat;

@Repository
public interface ScreeningJpaRepository extends JpaRepository<Screening, Long> {

    @Query("SELECT rs FROM ReservationSeat rs WHERE rs.id.screeningId = :screeningId")
    List<ReservationSeat> getReservationSeats(@Param("screeningId") Long screeningId);
}
