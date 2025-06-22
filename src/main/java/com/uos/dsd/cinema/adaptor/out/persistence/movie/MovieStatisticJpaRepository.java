package com.uos.dsd.cinema.adaptor.out.persistence.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uos.dsd.cinema.domain.movie.MovieStatisticId;
import com.uos.dsd.cinema.domain.movie.MovieStatistics;
import java.util.Optional;

@Repository
public interface MovieStatisticJpaRepository
        extends JpaRepository<MovieStatistics, MovieStatisticId> {

    Optional<MovieStatistics> findFirstByIdMovieIdOrderByIdStatisticDateDesc(Long movieId);
}
