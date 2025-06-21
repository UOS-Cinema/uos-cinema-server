package com.uos.dsd.cinema.adaptor.in.web.movie.response;

import java.time.LocalDate;

public record MovieSimpleResponse(
    Long id,
    String title,
    String posterUrls,
    LocalDate releaseDate
) {} 
