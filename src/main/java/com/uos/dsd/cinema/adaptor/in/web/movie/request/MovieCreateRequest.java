package com.uos.dsd.cinema.adaptor.in.web.movie.request;

import com.uos.dsd.cinema.domain.movie.enums.CastingType;
import com.uos.dsd.cinema.domain.movie.enums.MovieRating;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record MovieCreateRequest(
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
    
    public MovieCreateRequest {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("title은 필수입니다.");
        }
        if (runningTime == null) {
            throw new IllegalArgumentException("runningTime은 필수입니다.");
        }
        if (rating == null) {
            throw new IllegalArgumentException("rating은 필수입니다.");
        }
        if (releaseDate == null) {
            throw new IllegalArgumentException("releaseDate은 필수입니다.");
        }
        if (distributorName == null || distributorName.trim().isEmpty()) {
            throw new IllegalArgumentException("distributorName은 필수입니다.");
        }
        if (directorId == null) {
            throw new IllegalArgumentException("directorId는 필수입니다.");
        }
        if (screenTypes == null) screenTypes = new ArrayList<>();
        if (actors == null) actors = new ArrayList<>();
        if (genres == null) genres = new ArrayList<>();
    }

    public record ActorCastingRequest(
        Long actorId,
        String role,
        CastingType castingType
    ) {
        public ActorCastingRequest {
            if (actorId == null) {
                throw new IllegalArgumentException("actorId는 필수입니다.");
            }
            if (role == null || role.trim().isEmpty()) {
                throw new IllegalArgumentException("role은 필수입니다.");
            }
            if (castingType == null) {
                throw new IllegalArgumentException("castingType은 필수입니다.");
            }
        }
    }
} 
