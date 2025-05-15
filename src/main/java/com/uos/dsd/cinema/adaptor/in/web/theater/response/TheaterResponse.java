package com.uos.dsd.cinema.adaptor.in.web.theater.response;

import java.util.List;

import com.uos.dsd.cinema.domain.theater.enums.LayoutElement;

public record TheaterResponse(
    Long number,
    String name,
    List<List<LayoutElement>> layout,
    List<String> screenTypes
) {}
