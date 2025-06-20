package com.uos.dsd.cinema.domain.movie.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MovieSortType {

    RELEASE_DATE("release_date"),
    POPULARITY("popularity");

    private final String value;

    public static MovieSortType fromValue(String value) {
        for (MovieSortType sortType : values()) {
            if (sortType.getValue().equals(value)) {
                return sortType;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 정렬 기준입니다: " + value);
    }

    public static boolean isValidValue(String value) {
        try {
            fromValue(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
} 
