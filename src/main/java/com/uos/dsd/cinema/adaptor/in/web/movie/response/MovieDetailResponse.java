package com.uos.dsd.cinema.adaptor.in.web.movie.response;

import com.uos.dsd.cinema.domain.movie.enums.CastingType;
import com.uos.dsd.cinema.domain.movie.enums.MovieRating;

import java.time.LocalDate;
import java.util.List;

public record MovieDetailResponse(
    Long id,
    String title,
    String synopsis,
    Long runningTime,
    MovieRating rating,
    String posterUrls,
    LocalDate releaseDate,
    String distributorName,
    DirectorInfo director,
    List<ActorCastingInfo> actors,
    List<String> genres
) {
    
    public record DirectorInfo(
        Long id,
        String name,
        String photoUrl
    ) {}
    
    public record ActorCastingInfo(
        Long actorId,
        String actorName,
        String actorPhotoUrl,
        String role,
        CastingType castingType
    ) {}
} 
