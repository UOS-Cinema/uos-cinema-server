package com.uos.dsd.cinema.adaptor.in.web.movie.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record MovieListRequest(
    @Min(value = 0, message = "page는 0 이상이어야 합니다.")
    Integer page,

    @Min(value = 1, message = "size는 1 이상이어야 합니다.")
    @Max(value = 20, message = "size는 20을 초과할 수 없습니다.")
    Integer size
) {
    public MovieListRequest {
        if (page == null) page = 0;
        if (size == null) size = 20;
    }
} 
