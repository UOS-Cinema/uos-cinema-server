package com.uos.dsd.cinema.adaptor.out.persistence.screening;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uos.dsd.cinema.domain.screening.Screening;

@Repository
public interface ScreeningJpaRepository extends JpaRepository<Screening, Long> {
}
