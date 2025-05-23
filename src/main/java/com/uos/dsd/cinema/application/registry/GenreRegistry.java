package com.uos.dsd.cinema.application.registry;

import com.uos.dsd.cinema.application.port.out.genre.GenreRepository;
import com.uos.dsd.cinema.domain.genre.Genre;
import com.uos.dsd.cinema.domain.genre.GenreExceptionCode;
import com.uos.dsd.cinema.domain.genre.GenreReloadEvent;
import com.uos.dsd.cinema.common.exception.http.NotFoundException;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class GenreRegistry {

    private final GenreRepository genreRepository;

    private final Map<String, Genre> genreMap = new HashMap<>();
    
    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void register() {

        List<Genre> genres = genreRepository.findAll();
        genres.forEach(genre -> {
            genreMap.put(genre.getName(), genre);
        });
    }

    @EventListener
    public void reload(GenreReloadEvent event) {

        genreMap.clear();
        register();
    }

    public Genre get(String name) {

        Genre genre = genreMap.get(name);
        if (genre == null) {
            throw new NotFoundException(GenreExceptionCode.GENRE_NOT_FOUND);
        }
        return genre;
    }

    public List<Genre> getAll() {

        return new ArrayList<>(genreMap.values());
    }
}
