package com.uos.dsd.cinema.domain.theater;

import com.uos.dsd.cinema.common.model.Base;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "theater_seats")
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TheaterSeat extends Base {

    @EmbeddedId
    private TheaterSeatId id;

    private Boolean isAvailable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Theater theater;

    public TheaterSeat(
            Long theaterId, 
            String seatNumber, 
            Boolean isAvailable) {

        if (theaterId == null || theaterId <= 0) {
            throw new IllegalArgumentException("Theater ID is required");
        }
        if (seatNumber == null || seatNumber.isEmpty()) {
            throw new IllegalArgumentException("Seat number is required");
        }
        this.id = new TheaterSeatId(theaterId, seatNumber);
        this.isAvailable = isAvailable;
    }

    public void makeAvailable() {
        this.isAvailable = true;
    }

    public void makeUnavailable() {
        this.isAvailable = false;
    }

    public String getSeatNumber() {
        return id.getSeatNumber();
    }

    public Long getTheaterId() {
        return id.getTheaterId();
    }
}
