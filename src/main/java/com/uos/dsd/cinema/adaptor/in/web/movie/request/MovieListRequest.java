package com.uos.dsd.cinema.adaptor.in.web.movie.request;

public record MovieListRequest(
    Integer page,
    Integer size
) {
    public MovieListRequest {
        if (page == null) page = 0;
        if (size == null) size = 20;
    }
} 
