package com.uos.dsd.cinema.application.service.screening;

import org.springframework.stereotype.Service;

import com.uos.dsd.cinema.application.port.in.screening.command.ScreeningCreateCommand;
import com.uos.dsd.cinema.application.port.in.screening.command.ScreeningModifyCommand;
import com.uos.dsd.cinema.application.port.in.screening.usecase.ScreeningCreateUseCase;
import com.uos.dsd.cinema.application.port.in.screening.usecase.ScreeningDeleteUseCase;
import com.uos.dsd.cinema.application.port.in.screening.usecase.ScreeningModifyUseCase;

@Service
public class ScreeningService
        implements ScreeningCreateUseCase, ScreeningDeleteUseCase, ScreeningModifyUseCase {

    @Override
    public Long create(ScreeningCreateCommand command) {
        return null;
    }
    
    @Override
    public void modify(ScreeningModifyCommand command) {
    }

    @Override
    public void delete(Long id) {
    }
}
