package com.uos.dsd.cinema.adaptor.in.web.screening.request;

import com.uos.dsd.cinema.application.port.in.screening.command.ScreeningCreateCommand;

import java.time.LocalDateTime;

public record ScreeningCreateRequest(
    Long movieId,
    Long theaterId,
    String screenType,
    LocalDateTime startTime
) {

    public ScreeningCreateCommand toCommand() {

        return new ScreeningCreateCommand(
            movieId, 
            theaterId, 
            screenType, 
            startTime);
    }
}
