package com.uos.dsd.cinema.application.port.in.screening.command;

import com.uos.dsd.cinema.domain.screen_type.ScreenType;

import java.time.LocalDateTime;

public record ScreeningCreateCommand(
    Long movieId,
    Long theaterId,
    ScreenType screenType,
    LocalDateTime startTime
) {
}
