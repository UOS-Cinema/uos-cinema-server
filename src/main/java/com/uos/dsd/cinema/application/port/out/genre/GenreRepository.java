package com.uos.dsd.cinema.application.port.out.genre;

import java.util.List;
import java.util.Optional;

import com.uos.dsd.cinema.adaptor.in.web.genre.response.GenreResponse;
import com.uos.dsd.cinema.domain.genre.Genre;

public interface GenreRepository {

    Genre save(Genre genre);

    Optional<Genre> findById(String name);

    List<Genre> findAll();

    List<GenreResponse> findAllGenreResponses();

    int countAllMoviesByGenre(String name);

    void deleteById(String name);
}
