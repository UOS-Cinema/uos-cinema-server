package com.uos.dsd.cinema.application.port.out.point;

import com.uos.dsd.cinema.domain.point.PointTransaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PointTransactionRepository {

    PointTransaction save(PointTransaction pointTransaction);
    
    Page<PointTransaction> findByCustomerId(Long customerId, Pageable pageable);
    
    Optional<PointTransaction> findFirstByCustomerIdOrderByIdDesc(Long customerId);
    
    List<PointTransaction> findByPaymentId(Long paymentId);
} 
