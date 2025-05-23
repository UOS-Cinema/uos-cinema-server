package com.uos.dsd.cinema.domain.theater.enums;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LayoutElement {

    NONE(0),
    SEAT(1),
    AISLE(2);

    private final int code;

    public static LayoutElement of(int code) {

        return Arrays.stream(LayoutElement.values())
            .filter(element -> element.getCode() == code)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid layout element code: " + code));
    }
}
