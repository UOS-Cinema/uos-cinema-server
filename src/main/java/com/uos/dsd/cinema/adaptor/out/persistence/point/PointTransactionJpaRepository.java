package com.uos.dsd.cinema.adaptor.out.persistence.point;

import com.uos.dsd.cinema.application.port.out.point.PointTransactionRepository;
import com.uos.dsd.cinema.domain.point.PointTransaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PointTransactionJpaRepository 
        extends JpaRepository<PointTransaction, Long>, PointTransactionRepository {

    @Query(value = """
        SELECT pt FROM PointTransaction pt
        WHERE pt.customerId = :customerId
        ORDER BY pt.id DESC
    """)
    Page<PointTransaction> findByCustomerId(@Param("customerId") Long customerId, Pageable pageable);
    
    Optional<PointTransaction> findFirstByCustomerIdOrderByIdDesc(Long customerId);
    
    List<PointTransaction> findByPaymentId(Long paymentId);
} 
