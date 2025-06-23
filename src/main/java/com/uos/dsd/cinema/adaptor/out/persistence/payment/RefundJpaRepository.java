package com.uos.dsd.cinema.adaptor.out.persistence.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uos.dsd.cinema.domain.payment.Refund;

public interface RefundJpaRepository extends JpaRepository<Refund, Long> {
    
}
