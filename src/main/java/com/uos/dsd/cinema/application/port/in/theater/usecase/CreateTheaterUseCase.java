package com.uos.dsd.cinema.application.port.in.theater.usecase;

import com.uos.dsd.cinema.application.port.in.theater.command.CreateTheaterCommand;

public interface CreateTheaterUseCase {

    Long createTheater(CreateTheaterCommand command);
}
