package com.uos.dsd.cinema.application.port.out.screening.response;

import com.uos.dsd.cinema.domain.theater.enums.LayoutElement;

import java.time.LocalDateTime;
import java.util.List;

public record ScreeningResponse(

    Long id,
    Long movieId,
    String movieTitle,
    Long theaterId,
    String screenType,
    LocalDateTime startTime,
    LocalDateTime endTime,
    int duration,
    List<List<LayoutElement>> seatingStatus,
    int totalSeats,
    int availableSeats
) {

    public ScreeningResponse(
            Long id,
            Long movieId,
            String movieTitle,
            Long theaterId,
            String screenType,
            LocalDateTime startTime,
            int duration,
            List<List<LayoutElement>> seatingStatus) {

        this(id, 
            movieId,
            movieTitle, 
            theaterId, 
            screenType, 
            startTime, 
            startTime.plusMinutes(duration), 
            duration, 
            seatingStatus,
            seatingStatus.stream()
                .map(row -> row.stream()
                    .filter(element -> element == LayoutElement.SEAT
                    || element == LayoutElement.RESERVED)
                    .count())
                .reduce(0L, Long::sum)
                .intValue(),
            seatingStatus.stream()
                .map(row -> row.stream()
                    .filter(element -> element == LayoutElement.SEAT)
                    .count())
                .reduce(0L, Long::sum)
                .intValue());
    }
}