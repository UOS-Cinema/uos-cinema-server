package com.uos.dsd.cinema.adaptor.out.persistence.payment;

import com.uos.dsd.cinema.application.port.out.payment.PaymentRepository;
import com.uos.dsd.cinema.domain.payment.Payment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentJpaRepository 
        extends JpaRepository<Payment, Long>, PaymentRepository {

    Optional<Payment> findById(Long id);

    @Query("""
        SELECT p FROM Payment p
        WHERE p.reservationId IN (
            SELECT r.id FROM Reservation r
            WHERE r.customerId = :customerId
        )
        AND p.approvedAt IS NOT NULL
        ORDER BY p.approvedAt DESC
    """)
    Page<Payment> findApprovedPaymentsByCustomerId(@Param("customerId") Long customerId, Pageable pageable);
} 
