package com.uos.dsd.cinema.adaptor.out.persistence.movie;

import com.uos.dsd.cinema.application.port.out.movie.MovieRepository;
import com.uos.dsd.cinema.domain.movie.Movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.EntityGraph;

@Repository
public interface MovieJpaRepository
        extends 
    JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie>, MovieRepository {

    @EntityGraph(value = "Movie.withAll", type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT m FROM Movie m WHERE m.id = :id")
    Movie findWithDetail(Long id);
}
