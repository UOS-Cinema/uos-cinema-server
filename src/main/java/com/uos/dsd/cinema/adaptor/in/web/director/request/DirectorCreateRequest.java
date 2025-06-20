package com.uos.dsd.cinema.adaptor.in.web.director.request;

public record DirectorCreateRequest(
    String name,
    String photoUrl
) {
    public DirectorCreateRequest {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("감독명은 필수입니다.");
        }
    }
} 
