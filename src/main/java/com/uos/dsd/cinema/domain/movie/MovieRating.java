package com.uos.dsd.cinema.domain.movie;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MovieRating {
    
    ALL("전체 관람가"),
    TWELVE("12세 이상 관람가"),
    FIFTEEN("15세 이상 관람가"),
    EIGHTEEN("18세 이상 관람가");

    private final String value;
}
