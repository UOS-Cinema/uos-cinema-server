package com.uos.dsd.cinema.application.port.out.payment;

import com.uos.dsd.cinema.domain.payment.Payment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {

    Payment save(Payment payment);

    Optional<Payment> findById(Long id);

    List<Payment> findAll();

    Page<Payment> findApprovedPaymentsByCustomerId(Long customerId, Pageable pageable);
} 
