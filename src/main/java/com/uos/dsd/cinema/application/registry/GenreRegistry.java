package com.uos.dsd.cinema.application.registry;

import com.uos.dsd.cinema.adaptor.out.persistence.genre.GenreJpaRepository;
import com.uos.dsd.cinema.domain.genre.Genre;
import com.uos.dsd.cinema.domain.genre.GenreExceptionCode;
import com.uos.dsd.cinema.domain.genre.GenreReloadEvent;
import com.uos.dsd.cinema.common.exception.http.NotFoundException;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GenreRegistry extends LookupRegistry<Genre> {

    private final GenreJpaRepository genreJpaRepository;

    @Override
    protected RuntimeException notFoundException() {

        return new NotFoundException(GenreExceptionCode.GENRE_NOT_FOUND);
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void register() {

        super.register(genreJpaRepository.findAll(), Genre::getName);
    }

    @EventListener
    public void reload(GenreReloadEvent event) {

        super.register(genreJpaRepository.findAll(), Genre::getName);
    }
}
