package com.uos.dsd.cinema.adaptor.out.persistence.genre;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.uos.dsd.cinema.application.port.out.genre.GenreRepository;
import com.uos.dsd.cinema.application.registry.GenreRegistry;
import com.uos.dsd.cinema.domain.genre.Genre;
import com.uos.dsd.cinema.adaptor.in.web.genre.response.GenreResponse;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryAdapter implements GenreRepository {

    private final GenreJpaRepository genreJpaRepository;
    private final GenreRegistry genreRegistry;

    @Override
    public Genre save(Genre genre) {
        Genre savedGenre = genreJpaRepository.save(genre);
        genreRegistry.reload(null);
        return savedGenre;
    }

    @Override
    public Optional<Genre> findById(String name) {
        return genreJpaRepository.findById(name);
    }

    @Override
    public List<Genre> findAll() {
        return genreRegistry.getAll();
    }

    @Override
    public List<GenreResponse> findAllGenreResponses() {
        return genreRegistry.getAll().stream()
                .map(genre -> new GenreResponse(genre.getName(), genre.getDescription(), genre.getImageUrl()))
                .collect(Collectors.toList());
    }

    @Override
    public int countAllMoviesByGenre(String name) {
        return genreJpaRepository.countAllMoviesByGenre(name);
    }

    @Override
    public void deleteById(String name) {
        genreJpaRepository.deleteById(name);
        genreRegistry.reload(null);
    }
}
