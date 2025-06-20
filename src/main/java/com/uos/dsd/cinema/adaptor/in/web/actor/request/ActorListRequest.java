package com.uos.dsd.cinema.adaptor.in.web.actor.request;

public record ActorListRequest(
    Integer page,
    Integer size
) {
    public ActorListRequest {
        if (page == null) page = 0;
        if (size == null) size = 20;
    }
} 
