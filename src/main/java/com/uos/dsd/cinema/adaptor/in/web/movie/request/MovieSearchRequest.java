package com.uos.dsd.cinema.adaptor.in.web.movie.request;

import com.uos.dsd.cinema.domain.movie.enums.MovieSortType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record MovieSearchRequest(
    String query,
    LocalDate startDate,
    LocalDate endDate,
    List<String> genres,
    List<String> screenTypes,
    MovieSortType sortBy,
    Integer page,
    Integer size
) {
    public MovieSearchRequest {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("검색어는 필수입니다.");
        }
        
        // 기본 개봉일 필터링: 현재 날짜로부터 1달 전 ~ 7일 후
        LocalDate now = LocalDate.now();
        if (startDate == null) startDate = now.minusMonths(1);
        if (endDate == null) endDate = now.plusDays(7);

        if (genres == null) genres = new ArrayList<>();
        if (screenTypes == null) screenTypes = new ArrayList<>();
        
        if (sortBy == null) sortBy = MovieSortType.POPULARITY;
        if (page == null) page = 0;
        if (size == null) size = 20;
        if (size > 20) {
            throw new IllegalArgumentException("size는 20을 초과할 수 없습니다.");
        }
    }
} 
