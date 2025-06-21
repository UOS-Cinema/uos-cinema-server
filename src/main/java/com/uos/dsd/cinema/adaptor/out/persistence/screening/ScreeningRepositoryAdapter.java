package com.uos.dsd.cinema.adaptor.out.persistence.screening;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.uos.dsd.cinema.common.exception.http.NotFoundException;
import com.uos.dsd.cinema.domain.screening.exception.ScreeningExceptionCode;
import com.uos.dsd.cinema.domain.screening.Screening;
import com.uos.dsd.cinema.domain.reservation.ReservationSeat;
import com.uos.dsd.cinema.application.port.out.screening.ScreeningRepository;

@Repository
@RequiredArgsConstructor
public class ScreeningRepositoryAdapter implements ScreeningRepository {

    private final ScreeningJpaRepository screeningJpaRepository;

    @Override
    public Screening save(Screening screening) {
        return screeningJpaRepository.save(screening);
    }

    @Override
    public Optional<Screening> findById(Long id) {
        return screeningJpaRepository.findById(id);
    }

    @Override
    public List<Screening> findAllAround(LocalDateTime startTime, LocalDateTime endTime) {
        return screeningJpaRepository.findAllAround(startTime, endTime);
    }

    @Override
    public List<Screening> findAllByMovieAround(
            Long movieId, 
            LocalDateTime startTime, 
            LocalDateTime endTime) {
        return screeningJpaRepository.findAllByMovieAround(movieId, startTime, endTime);
    }

    @Override
    public List<Screening> findAllByTheaterAround(
            Long theaterId, 
            LocalDateTime startTime,
            LocalDateTime endTime) {
        return screeningJpaRepository.findAllByTheaterAround(theaterId, startTime, endTime);
    }

    @Override
    public List<Screening> findAllByMovieAndTheaterAround(
            Long movieId, 
            Long theaterId,
            LocalDateTime startTime, 
            LocalDateTime endTime) {
        return screeningJpaRepository.findAllByMovieAndTheaterAround(movieId, theaterId, startTime, endTime);
    }

    @Override
    public Screening getWithMovieAndTheater(Long id) {
        return screeningJpaRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(ScreeningExceptionCode.SCREENING_NOT_FOUND));
    }

    @Override
    public List<ReservationSeat> getReservationSeats(Long screeningId) {
        return screeningJpaRepository.getReservationSeats(screeningId);
    }

    @Override
    public void delete(Long id) {
        screeningJpaRepository.deleteById(id);
    }
}
