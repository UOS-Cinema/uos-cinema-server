package com.uos.dsd.cinema.adapter.in.movie;


import java.time.LocalDate;
import java.util.List;

import com.uos.dsd.cinema.adaptor.in.web.movie.request.MovieCreateRequest;
import com.uos.dsd.cinema.adaptor.in.web.movie.request.MovieUpdateRequest;
import com.uos.dsd.cinema.domain.movie.enums.MovieRating;
import com.uos.dsd.cinema.domain.movie.enums.CastingType;

public class AdminMovieUIFixture {

    public static final MovieCreateRequest createValidMovieCreateRequest 
        = new MovieCreateRequest(
            "Test Movie",
            "Test Synopsis",
            120,
            MovieRating.FIFTEEN,
            List.of("test-poster.jpg"),
            LocalDate.now(),
            "Test Distributor",
            1L,
            List.of("2D", "3D"),
            List.of(
                new MovieCreateRequest.ActorCastingRequest(1L, "김혜주", CastingType.LEAD),
                new MovieCreateRequest.ActorCastingRequest(2L, "최재원", CastingType.SPECIAL),
                new MovieCreateRequest.ActorCastingRequest(3L, "박정익", CastingType.SUPPORTING)
            ),
            List.of("Action", "Thriller")
        );
    
    public static final MovieUpdateRequest createValidMovieUpdateRequest 
            = new MovieUpdateRequest(
            "Test Movie",
            "Test Synopsis",
            180,
            MovieRating.EIGHTEEN,
            List.of("updated test-poster.jpg"),
            LocalDate.now(),
            "Test Distributor",
            1L,
            List.of("2D", "3D"),
            List.of(
                new MovieUpdateRequest.ActorCastingRequest(1L, "김혜주", CastingType.SUPPORTING),
                new MovieUpdateRequest.ActorCastingRequest(2L, "최재원", CastingType.LEAD),
                new MovieUpdateRequest.ActorCastingRequest(3L, "박정익", CastingType.SPECIAL)
            ),
            List.of("Action", "Thriller")
        );
}
