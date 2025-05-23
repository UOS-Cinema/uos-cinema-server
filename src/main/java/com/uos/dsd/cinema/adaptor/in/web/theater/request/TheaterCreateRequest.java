package com.uos.dsd.cinema.adaptor.in.web.theater.request;

import com.uos.dsd.cinema.domain.theater.enums.LayoutElement;

import java.util.List;

public record TheaterCreateRequest(
    Long number,
    String name,
    List<List<LayoutElement>> layout,
    List<String> screenTypes
) {}
