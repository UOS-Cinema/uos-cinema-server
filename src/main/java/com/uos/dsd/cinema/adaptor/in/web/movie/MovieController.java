package com.uos.dsd.cinema.adaptor.in.web.movie;

import com.uos.dsd.cinema.application.port.in.movie.query.MovieQueryCondition;
import com.uos.dsd.cinema.application.service.movie.MovieQueryService;
import com.uos.dsd.cinema.adaptor.in.web.movie.response.MovieResponse;
import com.uos.dsd.cinema.adaptor.in.web.movie.response.MovieElement;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.common.response.ApiResponse.PageResponse;
import com.uos.dsd.cinema.domain.movie.Movie;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieQueryService movieQueryService;

    @GetMapping("/{id}")
    public ApiResponse<MovieResponse> getMovie(@PathVariable("id") Long id) {

        MovieResponse movie = movieQueryService.getMovieResponse(id);
        return ApiResponse.success(movie);
    }

    @GetMapping
    public ApiResponse<PageResponse<MovieElement>> getMovies(
            @Valid @ModelAttribute MovieQueryCondition request) {

        Page<Movie> movies = movieQueryService.searchMovies(request);
        return ApiResponse.success(movies.map(MovieElement::from));
    }

    @GetMapping("/ranking")
    public ApiResponse<PageResponse<MovieElement>> getRankingMovies(
        @RequestParam(required = false, defaultValue = "0") Integer page,
        @RequestParam(required = false, defaultValue = "10") Integer size) {

        Page<Movie> movies = movieQueryService.getRankingMovies(page, size);
        return ApiResponse.success(movies.map(MovieElement::from));
    }

    @GetMapping("/upcoming")
    public ApiResponse<PageResponse<MovieElement>> getUpcomingMovies(
        @RequestParam(required = false, defaultValue = "0") Integer page,
        @RequestParam(required = false, defaultValue = "10") Integer size) {

        Page<Movie> movies = movieQueryService.getUpcomingMovies(page, size);
        return ApiResponse.success(movies.map(MovieElement::from));
    }
}
