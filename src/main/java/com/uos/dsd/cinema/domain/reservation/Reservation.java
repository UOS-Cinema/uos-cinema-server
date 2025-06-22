package com.uos.dsd.cinema.domain.reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.FetchType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.uos.dsd.cinema.domain.reservation.exception.ReservationExceptionCode;
import com.uos.dsd.cinema.common.exception.http.BadRequestException;
import com.uos.dsd.cinema.common.exception.http.UnauthorizedException;
import com.uos.dsd.cinema.core.converter.MapToJsonConverter;
import com.uos.dsd.cinema.core.converter.StringListConverter;
import com.uos.dsd.cinema.domain.reservation.constraint.ReservationConstraint;
import com.uos.dsd.cinema.domain.reservation.enums.ReservationStatus;

@Entity
@Table(name = "reservations")
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = {"id", "customerId", "screeningId"})
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "screening_id")
    private Long screeningId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReservationStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "seat_snapshot")
    @Convert(converter = StringListConverter.class)
    private List<String> seatSnapshot;

    @Convert(converter = MapToJsonConverter.class)
    @Column(name = "customer_count_snapshot")
    @Convert(converter = MapToJsonConverter.class)
    private Map<String, String> customerCountSnapshot;

    @OneToMany(mappedBy = "reservation",
            cascade = CascadeType.PERSIST, 
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ReservationSeat> reservationSeats = new ArrayList<>();

    @OneToMany(mappedBy = "reservation",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<ReservationCustomerCount> reservationCustomerCounts = new ArrayList<>();

    public Reservation(Long customerId,
                        Long screeningId,
                        Long theaterId,
                        List<String> seatNumbers, 
                        Map<String, String> customerCount) {
        this.customerId = customerId;
        this.screeningId = screeningId;
        this.status = ReservationStatus.PROGRESS;
        this.seatSnapshot = seatNumbers;
        this.customerCountSnapshot = customerCount;
        this.createdAt = LocalDateTime.now();
        if (seatNumbers.size() != customerCount.size()) {
            throw new BadRequestException(ReservationExceptionCode.INVALID_SEAT_AND_CUSTOMER_COUNT);
        }
        createReservationSeat(screeningId, theaterId, seatNumbers);
        createReservationCustomerCount(customerCount);
    }

    public void cancel(Long customerId) {
        if (!this.customerId.equals(customerId)) {
            throw new UnauthorizedException(ReservationExceptionCode.INVALID_CUSTOMER_ID);
        }
        this.status = ReservationStatus.CANCELLED;
        this.reservationSeats.clear();
        this.reservationCustomerCounts.clear();
    }

    public void complete() {
        if (this.status != ReservationStatus.PROGRESS
                || ReservationConstraint.isExceeded(this.createdAt)) {
            throw new BadRequestException(ReservationExceptionCode.RESERVATION_COMPLETION_TIME_LIMIT);
        }
        this.status = ReservationStatus.COMPLETED;
    }

    private void createReservationSeat(Long screeningId, Long theaterId, List<String> seatNumbers) {
        for (String seatNumber : seatNumbers) {
            this.reservationSeats
                .add(new ReservationSeat(this, screeningId, theaterId, seatNumber));
        }
    }

    private void createReservationCustomerCount(Map<String, String> customerCount) {
        for (Map.Entry<String, String> entry : customerCount.entrySet()) {
            this.reservationCustomerCounts
                .add(new ReservationCustomerCount(
                    this, entry.getKey(), Integer.parseInt(entry.getValue())));
        }
    }
}
