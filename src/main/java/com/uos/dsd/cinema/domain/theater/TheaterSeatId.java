package com.uos.dsd.cinema.domain.theater;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
@Embeddable
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class TheaterSeatId implements Serializable {

    private Long theaterId;
    private String seatNumber;
}
