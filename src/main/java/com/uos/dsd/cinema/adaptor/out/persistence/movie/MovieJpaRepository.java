package com.uos.dsd.cinema.adaptor.out.persistence.movie;

import com.uos.dsd.cinema.application.port.out.movie.MovieRepository;
import com.uos.dsd.cinema.domain.movie.Movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface MovieJpaRepository
        extends 
    JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie>, MovieRepository {

    @Query("""
        SELECT m
        FROM Movie m
        JOIN FETCH m.director d
        LEFT JOIN FETCH m.movieCasts mc
        LEFT JOIN FETCH mc.actor a
        LEFT JOIN FETCH m.genres g
        WHERE m.id = :id
    """)
    Movie findWithDetail(Long id);
}
