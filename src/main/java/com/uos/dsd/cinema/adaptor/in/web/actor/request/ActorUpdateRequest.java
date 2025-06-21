package com.uos.dsd.cinema.adaptor.in.web.actor.request;

import jakarta.validation.constraints.NotBlank;

public record ActorUpdateRequest(
    @NotBlank(message = "배우명은 필수입니다.")
    String name,
    String photoUrl
) {
} 
