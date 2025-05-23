package com.uos.dsd.cinema.application.port.out.genre;

import com.uos.dsd.cinema.domain.genre.Genre;

import java.util.List;

public interface GenreRepository {

    Genre save(Genre genre);

    List<Genre> findAll();

    void deleteById(String name);
}
