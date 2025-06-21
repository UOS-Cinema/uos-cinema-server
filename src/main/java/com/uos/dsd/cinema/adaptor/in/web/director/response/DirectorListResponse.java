package com.uos.dsd.cinema.adaptor.in.web.director.response;

import java.util.List;

public record DirectorListResponse(
    List<DirectorInfo> directors,
    Integer totalCount,
    Integer currentPage,
    Integer totalPages
) {
    
    public record DirectorInfo(
        Long id,
        String name,
        String photoUrl
    ) {}
} 
