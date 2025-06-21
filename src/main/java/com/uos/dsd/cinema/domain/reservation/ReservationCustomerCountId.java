package com.uos.dsd.cinema.domain.reservation;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
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

    @JoinColumn(name = "customer_type", referencedColumnName = "type", table = "customer_types")
    private String customerType;

    public ReservationCustomerCountId(String customerType) {
        this.customerType = customerType;
    }
}
