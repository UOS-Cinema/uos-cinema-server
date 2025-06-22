package com.uos.dsd.cinema.domain.movie;

import com.uos.dsd.cinema.domain.movie.enums.CastingType;

public record Casting(
    Long actorId,
    String role,
    CastingType castingType
) {

    public Casting(Long actorId, String role, CastingType castingType) {
        if (actorId == null || role == null || castingType == null) {
            throw new IllegalArgumentException("actorId, role, and castingType cannot be null.");
        }

        this.actorId = actorId;
        this.role = role;
        this.castingType = castingType;
    }
}
