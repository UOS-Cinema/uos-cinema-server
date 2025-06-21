package com.uos.dsd.cinema.domain.reservation;

import jakarta.persistence.Entity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "reservation_customer_counts")
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = {"id", "count"})
public class ReservationCustomerCount {

    @EmbeddedId
    private ReservationCustomerCountId id;

    private int count;

    @MapsId("reservationId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", updatable = false)
    private Reservation reservation;

    public ReservationCustomerCount(Reservation reservation, String customerType, int count) {
        this.id = new ReservationCustomerCountId(customerType);
        this.count = count;
        this.reservation = reservation;
    }
}
