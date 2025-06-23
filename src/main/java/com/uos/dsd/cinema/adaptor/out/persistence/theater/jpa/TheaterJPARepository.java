package com.uos.dsd.cinema.adaptor.out.persistence.theater.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uos.dsd.cinema.domain.theater.Theater;

public interface TheaterJPARepository extends JpaRepository<Theater, Long> {

    @Query("SELECT COUNT(s) FROM Screening s WHERE s.theater.number = :theaterNumber AND s.startTime > CURRENT_TIMESTAMP")
    int countByScreeningsFromNow(@Param("theaterNumber") Long theaterNumber);
}
