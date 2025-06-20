package com.uos.dsd.cinema.adaptor.in.web.director.response;

import java.util.List;

public record DirectorMovieListResponse(
    List<Long> movieIds,
    Integer totalCount,
    Integer currentPage,
    Integer totalPages
) {} 
