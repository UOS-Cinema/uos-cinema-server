package com.uos.dsd.cinema.domain.reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.data.domain.Persistable;

import com.uos.dsd.cinema.domain.reservation.exception.ReservationExceptionCode;
import com.uos.dsd.cinema.domain.screening.Screening;
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
public class Reservation implements Persistable<Long>{

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
    private Map<String, Integer> customerCountSnapshot;

    @OneToMany(mappedBy = "reservation",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}, 
            orphanRemoval = true, 
            fetch = FetchType.LAZY)
    private Set<ReservationSeat> reservationSeats = new HashSet<>();

    @OneToMany(mappedBy = "reservation",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<ReservationCustomerCount> reservationCustomerCounts = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screening_id", insertable = false, updatable = false)
    private Screening screening;

    public Reservation(Long customerId,
                        Screening screening,
                        Long theaterId,
                        List<String> seatNumbers, 
                        Map<String, Integer> customerCount) {
        this.customerId = customerId;
        if (!ReservationConstraint.canEnter(screening.getStartTime())) {
            throw new BadRequestException(ReservationExceptionCode.LAST_ENTRANCES_TIME_EXCEEDED);
        }
        this.screeningId = screening.getId();
        this.status = ReservationStatus.PROGRESS;
        this.seatSnapshot = seatNumbers;
        this.customerCountSnapshot = customerCount;
        this.createdAt = LocalDateTime.now();
    }
    
    public void afterPersist(Long theaterId) {
        validateSeatAndCustomerCount(this.seatSnapshot, this.customerCountSnapshot);
        createReservationSeat(this.screeningId, theaterId, this.seatSnapshot);
        createReservationCustomerCount(this.customerCountSnapshot);
    }

    public void cancel(Long customerId) {
        if (!this.customerId.equals(customerId)) {
            throw new UnauthorizedException(ReservationExceptionCode.INVALID_CUSTOMER_ID);
        }
        this.status = ReservationStatus.CANCELED;
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

    private void createReservationCustomerCount(Map<String, Integer> customerCount) {
        for (Map.Entry<String, Integer> entry : customerCount.entrySet()) {
            this.reservationCustomerCounts
                .add(new ReservationCustomerCount(
                    this, entry.getKey(), entry.getValue()));
        }
    }

    private void validateSeatAndCustomerCount(
            List<String> seatNumbers,
            Map<String, Integer> customerCount) {
        int totalCustomerCount = seatNumbers.size();
        for (Map.Entry<String, Integer> entry : customerCount.entrySet()) {
            totalCustomerCount -= entry.getValue();
        }
        if (totalCustomerCount != 0) {
            throw new BadRequestException(ReservationExceptionCode.INVALID_SEAT_AND_CUSTOMER_COUNT);
        }
    }

    @Override
    public boolean isNew() {
        return id == null;
    }

    @Override
    public Long getId() {
        return this.id;
    }
}
