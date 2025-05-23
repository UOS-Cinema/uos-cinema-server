package com.uos.dsd.cinema.adaptor.out.persistence.theater.jpa;

import com.uos.dsd.cinema.domain.theater.Theater;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterJPARepository extends JpaRepository<Theater, Long> {
}
