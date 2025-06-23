package com.uos.dsd.cinema.adaptor.out.persistence.payment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uos.dsd.cinema.domain.payment.Payment;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT p FROM Payment p WHERE p.reservationId = :reservationId AND p.status = 'COMPLETED'")
    Optional<Payment> findByReservationId(@Param("reservationId") Long reservationId);
}

