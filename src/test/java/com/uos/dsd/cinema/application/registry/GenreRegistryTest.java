package com.uos.dsd.cinema.application.registry;

import static org.assertj.core.api.Assertions.assertThat;

import com.uos.dsd.cinema.integration.IntegrationTest;
import com.uos.dsd.cinema.domain.genre.Genre;
import com.uos.dsd.cinema.domain.genre.GenreReloadEvent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.util.List;

public class GenreRegistryTest extends IntegrationTest {

    @AfterEach
    public void reloadGenreRegistry() {

        genreRegistry.reload(new GenreReloadEvent());
    }

    @Test
    public void screenTypeRegistryInitTest() {

        // when
        List<Genre> genres = genreRegistry.getAll();
        log.info("genres: {}", genres);

        // then
        assertThat(genres.size()).isGreaterThan(0);
    }

    @Test
    public void screenTypeRegistryGetTest() {

        // when
        Genre genre = genreRegistry.get("Action");

        // then
        assertThat(genre.getName()).isEqualTo("Action");
    }

    @Test
    public void screenTypeRegistryReloadTestWhenAdded() throws InterruptedException {

        // given
        int size = genreRegistry.getAll().size();
        Genre genre = new Genre("NEW", null, null);
        genreRepository.save(genre);

        // when
        GenreReloadEvent event = new GenreReloadEvent();
        eventPublisher.publishEvent(event);

        Thread.sleep(100);

        // then
        List<Genre> genres = genreRegistry.getAll();
        log.info("genres: {}", genres);
        assertThat(genres.size()).isEqualTo(size + 1);
    }
}
