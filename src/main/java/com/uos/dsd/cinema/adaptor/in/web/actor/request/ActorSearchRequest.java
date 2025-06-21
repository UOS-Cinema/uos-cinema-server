package com.uos.dsd.cinema.adaptor.in.web.actor.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ActorSearchRequest(
    @NotBlank(message = "검색어는 필수입니다.")
    String query,

    @Min(value = 0, message = "page는 0 이상이어야 합니다.")
    Integer page,

    @Min(value = 1, message = "size는 1 이상이어야 합니다.")
    @Max(value = 20, message = "size는 20을 초과할 수 없습니다.")
    Integer size
) {
    public ActorSearchRequest {
        if (page == null) page = 0;
        if (size == null) size = 20;
    }
}
