package com.uos.dsd.cinema.application.port.in.genre.usecase;

import com.uos.dsd.cinema.application.port.in.genre.command.CreateGenreCommand;

public interface CreateGenreUseCase {

    String createGenre(CreateGenreCommand command);
}
