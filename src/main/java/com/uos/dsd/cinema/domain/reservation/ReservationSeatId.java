package com.uos.dsd.cinema.domain.reservation;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ReservationSeatId implements Serializable {

    @JoinColumn(name = "screening_id", referencedColumnName = "id")
    private Long screeningId;

    @JoinColumn(name = "theater_id", referencedColumnName = "theater_id")
    private Long theaterId;

    @JoinColumn(name = "seat_number", referencedColumnName = "seat_number")
    private String seatNumber;
}
