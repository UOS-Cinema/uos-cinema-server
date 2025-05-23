package com.uos.dsd.cinema.domain.theater;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;

import java.io.Serializable;
@Embeddable
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class TheaterSeatId implements Serializable {

    @Column(name = "theater_id")
    private Long theaterId;

    @Column(name = "seat_number")
    private String seatNumber;
}
