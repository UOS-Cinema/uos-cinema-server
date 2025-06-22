package com.uos.dsd.cinema.application.port.in.genre.usecase;

import com.uos.dsd.cinema.application.port.in.genre.command.ModifyGenreCommand;

public interface ModifyGenreUseCase {

    String modifyGenre(ModifyGenreCommand command);
}
