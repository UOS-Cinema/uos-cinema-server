package com.uos.dsd.cinema.adaptor.in.web.movie.request;

import com.uos.dsd.cinema.domain.movie.enums.MovieSortType;

import java.time.LocalDate;
import java.util.List;

public record DirectorMovieSearchRequest(
    LocalDate startDate,
    LocalDate endDate,
    List<String> genres,
    List<String> screenTypes,
    String sortBy,
    Integer page,
    Integer size
) {
    public DirectorMovieSearchRequest {
        // 기본값 설정
        if (sortBy == null) sortBy = MovieSortType.POPULARITY.getValue();
        if (page == null) page = 0;
        if (size == null) size = 20;
        
        // 기본 개봉일 필터링: 현재 날짜로부터 1달 전 ~ 7일 후
        LocalDate now = LocalDate.now();
        if (startDate == null) startDate = now.minusMonths(1);
        if (endDate == null) endDate = now.plusDays(7);
        
        // 정렬 기준 검증
        if (!MovieSortType.isValidValue(sortBy)) {
            throw new IllegalArgumentException("유효하지 않은 정렬 기준입니다: " + sortBy);
        }
    }
} 
