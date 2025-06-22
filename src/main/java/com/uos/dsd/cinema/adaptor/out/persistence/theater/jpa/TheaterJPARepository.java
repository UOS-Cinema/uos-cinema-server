package com.uos.dsd.cinema.adaptor.out.persistence.theater.jpa;

import com.uos.dsd.cinema.domain.theater.Theater;
import com.uos.dsd.cinema.domain.theater.enums.LayoutElement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TheaterJPARepository extends JpaRepository<Theater, Long> {

    @Query("SELECT t.layout FROM Theater t WHERE t.number = :number")
    List<List<LayoutElement>> getSeatingStatus(@Param("number") Long number);
}
