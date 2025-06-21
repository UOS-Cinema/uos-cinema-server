package com.uos.dsd.cinema.adaptor.in.web.movie.response;

import java.util.List;

public record MovieListResponse(
    List<Long> movieIds,
    Integer totalCount,
    Integer currentPage,
    Integer totalPages
) {} 
