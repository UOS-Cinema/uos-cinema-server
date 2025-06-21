package com.uos.dsd.cinema.adaptor.in.web.actor.response;

import java.util.List;

public record ActorListResponse(
    List<ActorInfo> actors,
    Integer totalCount,
    Integer currentPage,
    Integer totalPages
) {
    
    public record ActorInfo(
        Long id,
        String name,
        String photoUrl
    ) {}
} 
