package com.uos.dsd.cinema.adaptor.out.persistence.screening;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uos.dsd.cinema.domain.screening.Screening;
import com.uos.dsd.cinema.domain.reservation.ReservationSeat;

@Repository
public interface ScreeningJpaRepository extends JpaRepository<Screening, Long> {

    @Query("""
    SELECT s FROM Screening s 
    WHERE s.startTime BETWEEN :startTime AND :endTime
    """)
    List<Screening> findAllAround(@Param("startTime") LocalDateTime startTime, 
                                @Param("endTime") LocalDateTime endTime);

    @Query("""
    SELECT s FROM Screening s 
    WHERE s.movieId = :movieId 
    AND s.startTime BETWEEN :startTime AND :endTime
    """)
    List<Screening> findAllByMovieAround(@Param("movieId") Long movieId,
                                @Param("startTime") LocalDateTime startTime,
                                @Param("endTime") LocalDateTime endTime);

    @Query("""
    SELECT s FROM Screening s 
    WHERE s.theaterId = :theaterId 
    AND s.startTime BETWEEN :startTime AND :endTime
    """)
    List<Screening> findAllByTheaterAround(@Param("theaterId") Long theaterId,
                                @Param("startTime") LocalDateTime startTime, 
                                @Param("endTime") LocalDateTime endTime);

    @Query("""
    SELECT s FROM Screening s 
    WHERE s.movieId = :movieId 
    AND s.theaterId = :theaterId 
    AND s.startTime BETWEEN :startTime AND :endTime
    """)
    List<Screening> findAllByMovieAndTheaterAround(@Param("movieId") Long movieId,
                                @Param("theaterId") Long theaterId,
                                @Param("startTime") LocalDateTime startTime, 
                                @Param("endTime") LocalDateTime endTime);

    @Query("""
    SELECT rs FROM ReservationSeat rs 
    WHERE rs.id.screeningId = :screeningId
    """)
    List<ReservationSeat> getReservationSeats(@Param("screeningId") Long screeningId);

    @Query("""
    SELECT s FROM Screening s
    JOIN FETCH s.movie
    JOIN FETCH s.theater
    """)
    List<Screening> findAllWithMovieAndTheater();
}
