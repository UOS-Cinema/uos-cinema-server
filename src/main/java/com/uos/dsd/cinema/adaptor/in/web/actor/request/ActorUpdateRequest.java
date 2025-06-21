package com.uos.dsd.cinema.adaptor.in.web.actor.request;

public record ActorUpdateRequest(
    String name,
    String photoUrl
) {
    public ActorUpdateRequest {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("배우명은 필수입니다.");
        }
    }
} 
