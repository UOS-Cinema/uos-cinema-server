package com.uos.dsd.cinema.adaptor.out.persistence.payment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uos.dsd.cinema.domain.payment.Point;

public interface PointJpaRepository extends JpaRepository<Point, Long> {

    /**
     * 특정 사용자의 가장 최근 포인트 정보를 조회 (ID 기준)
     */
    @Query("SELECT p FROM Point p WHERE p.customerId = :customerId ORDER BY p.id DESC")
    Optional<Point> findLatestByCustomerId(@Param("customerId") Long customerId);
}
