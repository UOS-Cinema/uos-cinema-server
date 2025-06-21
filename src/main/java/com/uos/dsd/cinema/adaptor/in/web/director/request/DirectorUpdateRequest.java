package com.uos.dsd.cinema.adaptor.in.web.director.request;

import jakarta.validation.constraints.NotBlank;

public record DirectorUpdateRequest(
    @NotBlank(message = "감독명은 필수입니다.")
    String name,
    String photoUrl
) {
} 
