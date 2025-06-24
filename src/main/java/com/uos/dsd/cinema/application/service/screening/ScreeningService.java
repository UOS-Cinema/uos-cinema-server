package com.uos.dsd.cinema.application.service.screening;

import java.util.List;
import java.time.LocalDateTime;
import java.time.LocalDate;

import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.uos.dsd.cinema.common.exception.http.NotFoundException;
import com.uos.dsd.cinema.application.port.in.screening.command.ScreeningModifyCommand;
import com.uos.dsd.cinema.application.port.in.screening.usecase.ScreeningDeleteUseCase;
import com.uos.dsd.cinema.application.port.in.screening.usecase.ScreeningModifyUseCase;
import com.uos.dsd.cinema.application.port.out.screening.ScreeningRepository;
import com.uos.dsd.cinema.domain.screening.Screening;
import com.uos.dsd.cinema.domain.screening.constraint.ScreeningConstraint;
import com.uos.dsd.cinema.domain.movie.Movie;
import com.uos.dsd.cinema.domain.screening.exception.ScreeningExceptionCode;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScreeningService implements ScreeningDeleteUseCase,
                                        ScreeningModifyUseCase {

    private final ScreeningRepository screeningRepository;

    @Transactional
    public Long create(Movie movie, Long theaterId, String screenType, LocalDateTime startTime) {
        int maxDuration = ScreeningConstraint.MAX_DURATION;
        List<Screening> screenings = screeningRepository.findAllByTheaterAround(
                theaterId,
                startTime.minusMinutes(maxDuration),
                startTime.plusMinutes(maxDuration));
        Screening screening = new Screening(movie, theaterId, screenType,
                startTime, screenings);
        return screeningRepository.save(screening).getId();
    }

    List<Screening> findAllAround(LocalDate date) {

        LocalDate startDate = date;
        LocalDate endDate;
    if (date == null) {
            startDate = LocalDate.now().minusDays(3);
            endDate = startDate.plusDays(3);
        } else {
            endDate = startDate.plusDays(1);
        }
        LocalDateTime startOfDay = startDate.atStartOfDay();
        LocalDateTime endOfDay = endDate.atStartOfDay();

        return screeningRepository.findAllAround(startOfDay, endOfDay);
    }

    List<Screening> findAllByMovieAround(Long movieId, LocalDate date) {

        LocalDate startDate = date;
        LocalDate endDate;
        if (date == null) {
            startDate = LocalDate.now().minusDays(3);
            endDate = startDate.plusDays(3);
        } else {
            endDate = startDate.plusDays(1);
        }
        LocalDateTime startOfDay = startDate.atStartOfDay();
        LocalDateTime endOfDay = endDate.atStartOfDay();

        return screeningRepository.findAllByMovieAround(movieId, startOfDay, endOfDay);
    }

    List<Screening> findAllByTheaterAround(Long theaterId, LocalDate date) {

        LocalDate startDate = date;
        LocalDate endDate;
        if (date == null) {
            startDate = LocalDate.now().minusDays(3);
            endDate = startDate.plusDays(3);
        } else {
            endDate = startDate.plusDays(1);
        }
        LocalDateTime startOfDay = startDate.atStartOfDay();
        LocalDateTime endOfDay = endDate.atStartOfDay();

        return screeningRepository.findAllByTheaterAround(theaterId, startOfDay, endOfDay);
    }

    List<Screening> findAllByMovieAndTheaterAround(Long movieId, Long theaterId, LocalDate date) {

        LocalDate startDate = date;
        LocalDate endDate;
        if (date == null) {
            startDate = LocalDate.now().minusDays(3);
            endDate = startDate.plusDays(3);
        } else {
            endDate = startDate.plusDays(1);
        }
        LocalDateTime startOfDay = startDate.atStartOfDay();
        LocalDateTime endOfDay = endDate.atStartOfDay();

        return screeningRepository.findAllByMovieAndTheaterAround(movieId, theaterId, startOfDay, endOfDay);
    }

    @Override
    @Transactional
    public void modify(ScreeningModifyCommand command) {
        Screening screening = screeningRepository.findById(command.id())
                .orElseThrow(
                        () -> new NotFoundException(ScreeningExceptionCode.SCREENING_NOT_FOUND));
        int maxDuration = ScreeningConstraint.MAX_DURATION;
        List<Screening> screenings = screeningRepository.findAllByTheaterAround(
                screening.getTheaterId(), 
                command.startTime().minusMinutes(maxDuration), 
                command.startTime().plusMinutes(maxDuration));
        screening.updateStartTime(command.startTime(), screenings);

        screeningRepository.save(screening);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException(ScreeningExceptionCode.SCREENING_NOT_FOUND));
        
        screening.delete();
        screeningRepository.save(screening);
    }
}
