package com.uos.dsd.cinema.application.port.in.screening.usecase;

import com.uos.dsd.cinema.application.port.in.screening.command.ScreeningModifyCommand;

public interface ScreeningModifyUseCase {

    void modify(ScreeningModifyCommand command);
}
