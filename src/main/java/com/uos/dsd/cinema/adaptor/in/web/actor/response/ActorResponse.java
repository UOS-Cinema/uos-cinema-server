package com.uos.dsd.cinema.adaptor.in.web.actor.response;

import com.uos.dsd.cinema.domain.actor.Actor;

public record ActorResponse(
    Long id,
    String name,
    String photoUrl
) {

    public static ActorResponse of(Actor actor) {
        return new ActorResponse(actor.getId(), actor.getName(), actor.getPhotoUrl());
    }
} 
