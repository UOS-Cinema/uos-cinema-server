package com.uos.dsd.cinema.application.service.movie;

import lombok.RequiredArgsConstructor;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.uos.dsd.cinema.domain.movie.Movie;
import com.uos.dsd.cinema.application.port.in.movie.query.MovieQueryCondition;
import com.uos.dsd.cinema.application.port.out.movie.MovieRepository;
import com.uos.dsd.cinema.domain.movie.enums.MovieSortType;
import com.uos.dsd.cinema.adaptor.in.web.movie.response.MovieResponse;
import com.uos.dsd.cinema.common.exception.http.NotFoundException;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MovieQueryService {

    private final MovieRepository movieRepository;

    public MovieResponse getMovieResponse(Long id) {

        Movie movie = movieRepository.findWithDetail(id);
        if (movie == null || movie.isDeleted()) {
            throw new NotFoundException("영화를 찾을 수 없습니다: " + id);
        }
        return MovieResponse.from(movie);
    }

    public Page<Movie> searchMovies(MovieQueryCondition request) {

        Specification<Movie> spec = Specification.where(MovieSpecification.notDeleted());

        Sort sort = resolveSort(request.sortBy());
        Pageable pageable = PageRequest.of(request.page(), request.size(), sort);

        if (request.startDate() != null && request.endDate() != null) {
            spec = spec.and(
                    MovieSpecification.hasReleaseDateBetween(request.startDate(), request.endDate()));
        }
        if (request.title() != null) {
            spec = spec.and(MovieSpecification.hasTitle(request.title()));
        }
        if (request.genres() != null && !request.genres().isEmpty()) {
            spec = spec.and(MovieSpecification.hasGenres(request.genres()));
        }
        if (request.screenTypes() != null && !request.screenTypes().isEmpty()) {
            spec = spec.and(MovieSpecification.hasScreenTypes(request.screenTypes()));
        }
        if (request.directorName() != null) {
            spec = spec.and(MovieSpecification.hasDirector(request.directorName()));
        }
        if (request.actorName() != null) {
            spec = spec.and(MovieSpecification.hasActor(request.actorName()));
        }

        return movieRepository.findAll(spec, pageable);
    }

    public Page<Movie> getRankingMovies(Integer page, Integer size) {
        MovieQueryCondition request = new MovieQueryCondition(
            null, null, null, LocalDate.now().minusMonths(2), LocalDate.now().plusDays(14), null, null,
                MovieSortType.POPULARITY, page, size);

        return searchMovies(request);
    }
    
    public Page<Movie> getUpcomingMovies(Integer page, Integer size) {
        MovieQueryCondition request = new MovieQueryCondition(null, null, null, LocalDate.now().plusDays(1), LocalDate.now().plusMonths(1), null, null,
                MovieSortType.RELEASE_DATE, page, size);

        return searchMovies(request);
    }

    private Sort resolveSort(MovieSortType sortBy) {
    if (MovieSortType.RELEASE_DATE.equals(sortBy)) {
        return Sort.by(Sort.Direction.DESC, "releaseDate");
        } else {
            return Sort.by(Sort.Direction.DESC, "cumulativeBookings");
        }
    }
}
