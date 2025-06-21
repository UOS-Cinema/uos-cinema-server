package com.uos.dsd.cinema.application.port.out.movie;

import java.util.Optional;

import com.uos.dsd.cinema.domain.movie.Movie;

public interface MovieRepository {

    Optional<Movie> findById(Long id);
}
