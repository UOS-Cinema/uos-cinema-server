package com.uos.dsd.cinema.application.port.in.movie.query;

import com.uos.dsd.cinema.domain.movie.enums.MovieSortType;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record MovieQueryCondition(
    String title,
    LocalDate startDate,
    LocalDate endDate,
    List<String> genres,
    List<String> screenTypes,
    MovieSortType sortBy,
    
    @Min(value = 0, message = "page는 0 이상이어야 합니다.")
    Integer page,

    @Min(value = 1, message = "size는 1 이상이어야 합니다.")
    @Max(value = 20, message = "size는 20을 초과할 수 없습니다.")
    Integer size
) {
    public MovieQueryCondition {
        if (genres == null) genres = new ArrayList<>();
        if (screenTypes == null) screenTypes = new ArrayList<>();
        
        if (sortBy == null) sortBy = MovieSortType.POPULARITY;
        if (page == null) page = 0;
        if (size == null) size = 10;
    }

    public static MovieQueryCondition of(
        String title,
        LocalDate startDate,
        LocalDate endDate,
        List<String> genres,
        List<String> screenTypes,
        MovieSortType sortBy,
        Integer page,
        Integer size) {
        return new MovieQueryCondition(title, startDate, endDate, genres, screenTypes, sortBy, page, size);
    }
} 
