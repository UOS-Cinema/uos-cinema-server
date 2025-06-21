package com.uos.dsd.cinema.application.port.in.screening.usecase;

import com.uos.dsd.cinema.application.port.in.screening.command.ScreeningCreateCommand;

public interface ScreeningCreateUseCase {

    Long create(ScreeningCreateCommand command);
}
