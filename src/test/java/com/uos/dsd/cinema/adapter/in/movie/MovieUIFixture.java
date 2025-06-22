package com.uos.dsd.cinema.adapter.in.movie;

import java.time.LocalDate;
import java.util.List;

import com.uos.dsd.cinema.application.port.in.movie.query.MovieQueryCondition;
import com.uos.dsd.cinema.domain.movie.enums.MovieSortType;

public class MovieUIFixture {

    public static final MovieQueryCondition defaultMovieQueryCondition
        = new MovieQueryCondition(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
            );
        
    public static final MovieQueryCondition thrillerQueryCondition
        = new MovieQueryCondition(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        );
        
    public static MovieQueryCondition startDateQueryCondition(LocalDate startDate) {
        return new MovieQueryCondition(
            null,
            null,
            null,
            startDate,
            null,
            null,
            null,
            null,
            null,
            null);
    }
    public static MovieQueryCondition  twoDQueryCondition = new MovieQueryCondition(
            null,
            null,
            null,
            null,
            null,
            null,
            List.of("2D"),
            null,
            null,
            null);
    
    public static MovieQueryCondition sortByReleaseDateQueryCondition = new MovieQueryCondition(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            MovieSortType.RELEASE_DATE,
            null,
            null);
}
