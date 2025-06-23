package com.uos.dsd.cinema.adaptor.in.web.movie.response;

import java.time.LocalDate;

import com.uos.dsd.cinema.domain.movie.Movie;

public record MovieElement(
    Long id,
    String title,
    String posterUrl,
    LocalDate releaseDate,
    int cumulativeBookings,
    String rating,
    String directorName,
    int runningTime
) {

    public static MovieElement from(Movie movie) {
        return new MovieElement(
            movie.getId(),
            movie.getTitle(),
            movie.getPosterUrls().get(0),
            movie.getReleaseDate(),
            movie.getCumulativeBookings(),
            movie.getRating().getValue(),
            movie.getDirector().getName(),
            movie.getRunningTime()
        );
    }
} 
