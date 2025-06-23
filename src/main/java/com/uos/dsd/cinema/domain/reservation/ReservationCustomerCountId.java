package com.uos.dsd.cinema.domain.reservation;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ReservationCustomerCountId implements Serializable {

    @Column(name = "reservation_id")
    private Long reservationId;

    @Column(name = "customer_type")
    private String customerType;

    public ReservationCustomerCountId(String customerType) {
        this.customerType = customerType;
    }
}
