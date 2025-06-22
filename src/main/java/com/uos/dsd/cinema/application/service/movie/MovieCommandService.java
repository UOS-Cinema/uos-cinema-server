package com.uos.dsd.cinema.application.service.movie;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uos.dsd.cinema.application.port.out.movie.MovieRepository;
import com.uos.dsd.cinema.adaptor.out.persistence.movie.MovieStatisticJpaRepository;
import com.uos.dsd.cinema.adaptor.in.web.movie.request.MovieCreateRequest;
import com.uos.dsd.cinema.adaptor.in.web.movie.request.MovieUpdateRequest;
import com.uos.dsd.cinema.common.exception.http.BadRequestException;
import com.uos.dsd.cinema.common.exception.http.NotFoundException;
import com.uos.dsd.cinema.domain.movie.Movie;
import com.uos.dsd.cinema.domain.screen_type.ScreenType;
import com.uos.dsd.cinema.domain.genre.Genre;
import com.uos.dsd.cinema.domain.movie.Casting;
import com.uos.dsd.cinema.domain.movie.MovieStatistics;
import com.uos.dsd.cinema.application.port.out.screening.ScreeningRepository;

// TODO: 추후 의존성 관리...
@Service
@Transactional
@RequiredArgsConstructor
public class MovieCommandService {

    private final MovieRepository movieRepository;
    private final MovieStatisticJpaRepository movieStatisticJpaRepository;
    private final ScreeningRepository screeningRepository;

    public Movie createMovie(MovieCreateRequest request) {
        List<Casting> castings = request.actors().stream()
            .map(actor -> new Casting(actor.actorId(), actor.role(), actor.castingType()))
            .collect(Collectors.toList());
            
        Set<ScreenType> screenTypes = request.screenTypes().stream()
            .map(type -> ScreenType.reference(type))
            .collect(Collectors.toSet());

        Set<Genre> genres = request.genres().stream()
            .map(name -> Genre.reference(name))
                .collect(Collectors.toSet());

        Movie movie = new Movie(
                request.title(),
                request.synopsis(),
                request.runningTime(),
                request.rating(),
                request.posterUrls(),
                request.releaseDate(),
                request.distributor(),
                request.directorId(),
                screenTypes,
                castings, 
                genres);

        movieRepository.save(movie);
        movieStatisticJpaRepository.save(new MovieStatistics(
            LocalDate.now(),
            movie,
            0
        ));

        return movie;
    }

    public Movie updateMovie(Long id, MovieUpdateRequest request) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("영화를 찾을 수 없습니다."));
        if (movie.isDeleted()) {
            throw new BadRequestException("삭제된 영화는 수정할 수 없습니다.");
        }
        if (screeningRepository.countByMovie(id) > 0) {
            throw new BadRequestException("상영 예정 중이고 이미 예약된 영화는 수정할 수 없습니다.");
        }

        List<Casting> castings = request.actors().stream()
            .map(actor -> new Casting(actor.actorId(), actor.role(), actor.castingType()))
            .collect(Collectors.toList());

        Set<ScreenType> screenTypes = request.screenTypes().stream()
            .map(type -> ScreenType.reference(type))
            .collect(Collectors.toSet());

        Set<Genre> genres = request.genres().stream()
            .map(name -> Genre.reference(name))
            .collect(Collectors.toSet());

        movie.update(request.title(),
                    request.synopsis(),
                    request.runningTime(),
                    request.rating(),
                    request.posterUrls(),
                    request.releaseDate(),
                    request.distributor(),
                    request.directorId(),
                    screenTypes,
                    castings,
                    genres);
        movieRepository.save(movie);
        return movie;
    }

    public void deleteMovie(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("영화를 찾을 수 없습니다."));
        if (screeningRepository.countByMovie(id) > 0) {
            throw new BadRequestException("상영 예정 중이고 이미 예약된 영화는 삭제할 수 없습니다.");
        }
        movie.delete();
        movieRepository.save(movie);
    }

}
