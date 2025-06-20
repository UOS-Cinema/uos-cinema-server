package com.uos.dsd.cinema.adaptor.in.web.actor.request;

public record ActorSearchRequest(
    String query,
    Integer page,
    Integer size
) {
    public ActorSearchRequest {
        if (page == null) page = 0;
        if (size == null) size = 20;
    }
} 
