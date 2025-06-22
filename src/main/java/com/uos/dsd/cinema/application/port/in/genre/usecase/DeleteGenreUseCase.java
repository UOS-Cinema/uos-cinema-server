package com.uos.dsd.cinema.application.port.in.genre.usecase;

import com.uos.dsd.cinema.application.port.in.genre.command.DeleteGenreCommand;

public interface DeleteGenreUseCase {

    void deleteGenre(DeleteGenreCommand command);
}
