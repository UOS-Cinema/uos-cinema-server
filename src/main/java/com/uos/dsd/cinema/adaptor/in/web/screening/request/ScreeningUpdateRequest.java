package com.uos.dsd.cinema.adaptor.in.web.screening.request;

import com.uos.dsd.cinema.application.port.in.screening.command.ScreeningModifyCommand;

import java.time.LocalDateTime;

public record ScreeningUpdateRequest(
    LocalDateTime startTime
) {

    public ScreeningModifyCommand toCommand(Long id) {
        return new ScreeningModifyCommand(id, startTime);
    }
}
