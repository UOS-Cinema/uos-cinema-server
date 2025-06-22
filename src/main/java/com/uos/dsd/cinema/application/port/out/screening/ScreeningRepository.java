package com.uos.dsd.cinema.application.port.out.screening;

import com.uos.dsd.cinema.domain.screening.Screening;
import com.uos.dsd.cinema.domain.reservation.ReservationSeat;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

public interface ScreeningRepository {

    Screening save(Screening screening);

    Optional<Screening> findById(Long id);

    List<Screening> findAllAround(LocalDateTime startTime, LocalDateTime endTime);

    List<Screening> findAllByMovieAround(
            Long movieId, 
            LocalDateTime startTime, 
            LocalDateTime endTime);

    List<Screening> findAllByTheaterAround(
            Long theaterId, 
            LocalDateTime startTime,
            LocalDateTime endTime);

    List<Screening> findAllByMovieAndTheaterAround(
            Long movieId, 
            Long theaterId,
            LocalDateTime startTime, 
            LocalDateTime endTime);

    Screening getWithMovieAndTheater(Long id);

    List<ReservationSeat> getReservationSeats(Long screeningId);

    int countByMovie(Long movieId);

    void delete(Long id);
}
