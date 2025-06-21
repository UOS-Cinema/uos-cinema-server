package com.uos.dsd.cinema.adaptor.in.web.movie.request;

import com.uos.dsd.cinema.domain.movie.enums.CastingType;
import com.uos.dsd.cinema.domain.movie.enums.MovieRating;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record MovieUpdateRequest(
    @NotBlank(message = "title은 필수입니다.")
    String title,
    String synopsis,

    @NotNull(message = "runningTime은 필수입니다.")
    Long runningTime,

    @NotNull(message = "rating은 필수입니다.")
    MovieRating rating,
    String posterUrls,

    @NotNull(message = "releaseDate은 필수입니다.")
    LocalDate releaseDate,

    @NotBlank(message = "distributorName은 필수입니다.")
    String distributorName,

    @NotNull(message = "directorId는 필수입니다.")
    Long directorId,

    List<String> screenTypes,

    @Valid
    List<ActorCastingRequest> actors,
    
    List<String> genres
) {
    public MovieUpdateRequest {
        if (screenTypes == null) screenTypes = new ArrayList<>();
        if (actors == null) actors = new ArrayList<>();
        if (genres == null) genres = new ArrayList<>();
    }
    public record ActorCastingRequest(
        @NotNull(message = "actorId는 필수입니다.")
        Long actorId,

        @NotBlank(message = "role은 필수입니다.")
        String role,

        @NotNull(message = "castingType은 필수입니다.")
        CastingType castingType
    ) {
    }
} 
