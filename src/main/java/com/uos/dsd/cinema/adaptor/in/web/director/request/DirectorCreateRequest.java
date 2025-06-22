package com.uos.dsd.cinema.adaptor.in.web.director.request;

import jakarta.validation.constraints.NotBlank;

public record DirectorCreateRequest(
    @NotBlank(message = "이름은 필수입니다.")
    String name,
    String photoUrl
) {
} 
