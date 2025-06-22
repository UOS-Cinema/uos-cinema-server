package com.uos.dsd.cinema.application.port.in.screening.query;

import java.time.LocalDate;

public record SearchScreeningQuery(
    Long movieId, 
    Long theaterId, 
    LocalDate date
) {
}
