package com.uos.dsd.cinema.adaptor.in.web.director.request;

public record DirectorSearchRequest(
    String query,
    Integer page,
    Integer size
) {
    public DirectorSearchRequest {
        if (page == null) page = 0;
        if (size == null) size = 20;
    }
} 
