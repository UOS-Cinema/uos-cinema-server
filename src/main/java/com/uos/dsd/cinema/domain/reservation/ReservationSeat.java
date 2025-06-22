package com.uos.dsd.cinema.domain.reservation;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.Column;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "reservation_seats")
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = {"id"})
public class ReservationSeat {

    @EmbeddedId
    private ReservationSeatId id;

    @Column(name = "reservation_id")
    private Long reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", insertable = false, updatable = false)
    private Reservation reservation;

    public ReservationSeat(Reservation reservation, Long screeningId, Long theaterId, String seatNumber) {
        this.id = new ReservationSeatId(screeningId, theaterId, seatNumber);
        this.reservation = reservation;
        this.reservationId = reservation.getId();
    }
}
