package com.uos.dsd.cinema.adaptor.out.persistence.screen_type.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uos.dsd.cinema.domain.screen_type.ScreenType;

public interface ScreenTypeJPARepository extends JpaRepository<ScreenType, String> {

}
