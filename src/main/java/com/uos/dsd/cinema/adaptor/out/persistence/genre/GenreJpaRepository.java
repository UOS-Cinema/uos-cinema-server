package com.uos.dsd.cinema.adaptor.out.persistence.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uos.dsd.cinema.domain.genre.Genre;

@Repository
public interface GenreJpaRepository extends JpaRepository<Genre, String> {
    
    @Query(value = "SELECT COUNT(*) FROM movie_genres WHERE genre_name = :name", 
            nativeQuery = true)
    int countAllMoviesByGenre(@Param("name") String name);
}
