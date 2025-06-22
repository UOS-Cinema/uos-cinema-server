package com.uos.dsd.cinema.application.port.out.movie;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.uos.dsd.cinema.domain.movie.Movie;

public interface MovieRepository {

    Movie save(Movie movie);

    Optional<Movie> findById(Long id);

    Page<Movie> findAll(Specification<Movie> spec, Pageable pageable);

    Movie findWithDetail(Long id);
}
