package com.uos.dsd.cinema.adaptor.out.persistence.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uos.dsd.cinema.application.port.out.genre.GenreRepository;
import com.uos.dsd.cinema.domain.genre.Genre;

@Repository
public interface GenreJpaRepository extends JpaRepository<Genre, String>, GenreRepository {
}
