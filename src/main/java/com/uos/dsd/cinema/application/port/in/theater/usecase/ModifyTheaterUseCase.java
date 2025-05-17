package com.uos.dsd.cinema.application.port.in.theater.usecase;

import com.uos.dsd.cinema.application.port.in.theater.command.ModifyTheaterCommand;

public interface ModifyTheaterUseCase {

    Long modifyTheater(ModifyTheaterCommand command);
}