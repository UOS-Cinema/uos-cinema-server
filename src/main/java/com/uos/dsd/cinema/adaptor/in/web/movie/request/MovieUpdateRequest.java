package com.uos.dsd.cinema.adaptor.in.web.movie.request;

import com.uos.dsd.cinema.domain.movie.enums.CastingType;
import com.uos.dsd.cinema.domain.movie.enums.MovieRating;

import java.time.LocalDate;
import java.util.List;

public record MovieUpdateRequest(
    String title,
    String synopsis,
    Long runningTime,
    MovieRating rating,
    String posterUrls,
    LocalDate releaseDate,
    String distributorName,
    Long directorId,
    List<String> screenTypes,
    List<ActorCastingRequest> actors,
    List<String> genres
) {
    
    public record ActorCastingRequest(
        Long actorId,
        String role,
        CastingType castingType
    ) {}
} 
