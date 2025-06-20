package com.uos.dsd.cinema.adaptor.in.web.movie.response;

import com.uos.dsd.cinema.domain.movie.enums.CastingType;

import java.util.List;

public record ActorMovieResponse(
    List<ActorMovieInfo> movies,
    Integer totalCount,
    Integer currentPage,
    Integer totalPages
) {
    
    public record ActorMovieInfo(
        Long movieId,
        String role,
        CastingType castingType
    ) {}
} 
