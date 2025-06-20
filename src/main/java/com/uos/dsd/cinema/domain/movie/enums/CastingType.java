package com.uos.dsd.cinema.domain.movie.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CastingType {

    LEAD("주연"),
    SUPPORTING("조연"),
    SPECIAL("특별출연");

    private final String value;
} 
