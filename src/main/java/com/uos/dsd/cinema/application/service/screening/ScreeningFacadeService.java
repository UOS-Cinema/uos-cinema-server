package com.uos.dsd.cinema.application.service.screening;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uos.dsd.cinema.application.port.in.screening.query.SearchScreeningQuery;
import com.uos.dsd.cinema.common.exception.http.NotFoundException;
import com.uos.dsd.cinema.application.port.in.screening.usecase.GetScreeningTheaterLayoutUseCase;
import com.uos.dsd.cinema.application.port.in.screening.usecase.ScreeningCreateUseCase;
import com.uos.dsd.cinema.application.port.in.screening.usecase.SearchScreeningUseCase;
import com.uos.dsd.cinema.application.port.in.screening.usecase.GetScreeningDetailUseCase;
import com.uos.dsd.cinema.application.port.in.screening.command.ScreeningCreateCommand;
import com.uos.dsd.cinema.application.port.out.screening.ScreeningRepository;
import com.uos.dsd.cinema.application.port.out.movie.MovieRepository;
import com.uos.dsd.cinema.application.port.out.screening.response.ScreeningResponse;
import com.uos.dsd.cinema.application.port.out.theater.TheaterRepository;
import com.uos.dsd.cinema.domain.screening.Screening;
import com.uos.dsd.cinema.domain.theater.enums.LayoutElement;
import com.uos.dsd.cinema.domain.reservation.ReservationSeat;
import com.uos.dsd.cinema.domain.movie.Movie;
import com.uos.dsd.cinema.domain.movie.exception.MovieExceptionCode;
import com.uos.dsd.cinema.domain.screening.exception.ScreeningExceptionCode;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScreeningFacadeService implements
                                        ScreeningCreateUseCase,
                                        GetScreeningTheaterLayoutUseCase, 
                                        SearchScreeningUseCase,
                                        GetScreeningDetailUseCase {

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;
    private final ScreeningService screeningService;

    @Override
    public Long create(ScreeningCreateCommand command) {
        Movie movie = movieRepository.findById(command.movieId())
                .orElseThrow(() -> new NotFoundException(MovieExceptionCode.MOVIE_NOT_FOUND));
        return screeningService
                .create(
            movie, command.theaterId(), command.screenType(), command.startTime());
    }

    @Override
    public ScreeningResponse get(Long id) {
        Screening screening = screeningRepository.getWithMovieAndTheater(id);
        return new ScreeningResponse(
            screening.getId(),
            screening.getMovieId(),
            screening.getMovie().getTitle(),
            screening.getTheaterId(),
            screening.getScreenType().getType(),
            screening.getStartTime(),
            screening.getDuration(),
            getScreeningTheaterLayout(screening.getTheaterId())
        );
    }

    @Override
    public List<ScreeningResponse> search(SearchScreeningQuery query) {

        if (query.theaterId() != null && query.movieId() != null) {
            List<Screening> screenings = screeningService.findAllByMovieAndTheaterAround(
                    query.movieId(), query.theaterId(), query.date());
            Movie movie = movieRepository.findById(query.movieId())
                    .orElseThrow(() -> new NotFoundException(MovieExceptionCode.MOVIE_NOT_FOUND));
            return screenings.stream()
                .map(screening -> new ScreeningResponse(
                    screening.getId(),
                    screening.getMovieId(),
                    movie.getTitle(),
                    screening.getTheaterId(),
                    screening.getScreenType().getType(),
                    screening.getStartTime(),
                    screening.getDuration(),
                    getScreeningTheaterLayout(screening.getTheaterId())
                ))
                .toList();
        } else if (query.theaterId() != null) {
            List<Screening> screenings =
                    screeningService.findAllByTheaterAround(query.theaterId(), query.date());
            return screenings.stream()
                .map(screening -> new ScreeningResponse(
                    screening.getId(),
                    screening.getMovieId(),
                    screening.getMovie().getTitle(),
                    screening.getTheaterId(),
                    screening.getScreenType().getType(),
                    screening.getStartTime(),
                    screening.getDuration(),
                    getScreeningTheaterLayout(screening.getTheaterId())
                ))
                .toList();
        } else if (query.movieId() != null) {
            List<Screening> screenings =
                    screeningService.findAllByMovieAround(query.movieId(), query.date());
            Movie movie = movieRepository.findById(query.movieId())
                    .orElseThrow(() -> new NotFoundException(MovieExceptionCode.MOVIE_NOT_FOUND));
            return screenings.stream()
                    .map(screening -> new ScreeningResponse(
                    screening.getId(),
                    screening.getMovieId(),
                    movie.getTitle(),
                    screening.getTheaterId(),
                    screening.getScreenType().getType(),
                    screening.getStartTime(),
                    screening.getDuration(),
                    getScreeningTheaterLayout(screening.getTheaterId())
                ))
                .toList();
        } else {
            List<Screening> screenings = screeningService.findAllAround(query.date());
            return screenings.stream()
                .map(screening -> new ScreeningResponse(
                    screening.getId(),
                    screening.getMovieId(),
                    screening.getMovie().getTitle(),
                    screening.getTheaterId(),
                    screening.getScreenType().getType(),
                    screening.getStartTime(),
                    screening.getDuration(),
                    getScreeningTheaterLayout(screening.getTheaterId())
                ))
                .toList();
        }
    }

    @Override
    public List<List<LayoutElement>> getScreeningTheaterLayout(Long id) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ScreeningExceptionCode.SCREENING_NOT_FOUND));
        List<List<LayoutElement>> layout = theaterRepository.getSeatingStatus(screening.getTheaterId());
        List<ReservationSeat> reservationSeats = screeningRepository.getReservationSeats(id);

        char row = 'A';
        int column = 0;

        for (int i = 0; i < layout.size(); i++) {
            for (int j = 0; j < layout.get(i).size(); j++) {
                if (layout.get(i).get(j) == LayoutElement.SEAT) {
                    column++;
                    String seatNumber = row + "" + column;
                    if (reservationSeats.stream()
                            .anyMatch(rs -> rs.getId().getSeatNumber().equals(seatNumber))) {
                        layout.get(i).set(j, LayoutElement.RESERVED);
                    }
                }
            }
            if (column != 0) {
                row++;
                column = 0;
            }
        }
        return layout;
    }
}
