package com.uos.dsd.cinema.application.service.genre;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uos.dsd.cinema.application.port.in.genre.command.CreateGenreCommand;
import com.uos.dsd.cinema.application.port.in.genre.command.DeleteGenreCommand;
import com.uos.dsd.cinema.application.port.in.genre.command.ModifyGenreCommand;
import com.uos.dsd.cinema.application.port.in.genre.usecase.CreateGenreUseCase;
import com.uos.dsd.cinema.application.port.in.genre.usecase.DeleteGenreUseCase;
import com.uos.dsd.cinema.application.port.in.genre.usecase.ModifyGenreUseCase;
import com.uos.dsd.cinema.application.port.out.genre.GenreRepository;
import com.uos.dsd.cinema.domain.genre.Genre;
import com.uos.dsd.cinema.common.exception.http.NotFoundException;
import com.uos.dsd.cinema.common.exception.http.BadRequestException;

@Service
@Transactional
@RequiredArgsConstructor
public class GenreService implements CreateGenreUseCase, ModifyGenreUseCase, DeleteGenreUseCase {

    private final GenreRepository genreRepository;

    @Override
    public String createGenre(CreateGenreCommand command) {
        Genre genre = new Genre(command.name(), command.description(), command.imageUrl());
        return genreRepository.save(genre).getName();
    }

    @Override
    public String modifyGenre(ModifyGenreCommand command) {
        Genre genre = genreRepository.findById(command.name())
                .orElseThrow(() -> new NotFoundException());

        genre.modifyDescription(command.description());
        genre.modifyImageUrl(command.imageUrl());
        genreRepository.save(genre);
        return genre.getName();
    }

    @Override
    public void deleteGenre(DeleteGenreCommand command) {
        if (genreRepository.countAllMoviesByGenre(command.name()) > 0) {
            throw new BadRequestException("장르에 속한 영화가 있습니다.");
        }
        genreRepository.deleteById(command.name());
    }
    
}
