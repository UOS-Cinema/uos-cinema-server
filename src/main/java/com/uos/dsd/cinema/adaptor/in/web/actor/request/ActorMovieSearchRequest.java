package com.uos.dsd.cinema.adaptor.in.web.actor.request;

import com.uos.dsd.cinema.domain.movie.enums.MovieSortType;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record ActorMovieSearchRequest(
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
    public ActorMovieSearchRequest {
        
        // 기본 개봉일 필터링: 현재 날짜로부터 1달 전 ~ 7일 후
        LocalDate now = LocalDate.now();
        if (startDate == null) startDate = now.minusMonths(1);
        if (endDate == null) endDate = now.plusDays(7);

        if (genres == null) genres = new ArrayList<>();
        if (screenTypes == null) screenTypes = new ArrayList<>();
        
        if (sortBy == null) sortBy = MovieSortType.POPULARITY;
        if (page == null) page = 0;
        if (size == null) size = 20;
    }
} 
