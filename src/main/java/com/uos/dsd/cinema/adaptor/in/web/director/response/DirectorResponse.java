package com.uos.dsd.cinema.adaptor.in.web.director.response;

import com.uos.dsd.cinema.domain.director.Director;

public record DirectorResponse(
    Long id,
    String name,
    String photoUrl
) {

    public static DirectorResponse of(Director director) {
        return new DirectorResponse(director.getId(), director.getName(), director.getPhotoUrl());
    }
} 
