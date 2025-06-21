package com.uos.dsd.cinema.adaptor.out.persistence.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uos.dsd.cinema.application.port.out.movie.MovieRepository;
import com.uos.dsd.cinema.domain.movie.Movie;

@Repository
public interface MovieJpaRepository extends JpaRepository<Movie, Long>, MovieRepository {
}
