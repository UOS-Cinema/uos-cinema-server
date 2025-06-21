package com.uos.dsd.cinema.application.port.in.screening.command;

import java.time.LocalDateTime;

public record ScreeningCreateCommand(
    Long movieId,
    Long theaterId,
    String screenType,
    LocalDateTime startTime
) {
}
