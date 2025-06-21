package com.uos.dsd.cinema.application.port.in.screening.command;

import java.time.LocalDateTime;

public record ScreeningModifyCommand(
    Long id,
    LocalDateTime startTime
) {
}
