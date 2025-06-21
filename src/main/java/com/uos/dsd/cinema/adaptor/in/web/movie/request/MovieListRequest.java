package com.uos.dsd.cinema.adaptor.in.web.movie.request;

public record MovieListRequest(
    Integer page,
    Integer size
) {
    public MovieListRequest {
        if (page == null) page = 0;
        if (size == null) size = 20;
        if (size > 20) {
            throw new IllegalArgumentException("size는 20을 초과할 수 없습니다.");
        }
    }
} 
