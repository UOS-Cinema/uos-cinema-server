package com.uos.dsd.cinema.adaptor.out.persistence.screen_type;

import com.uos.dsd.cinema.application.port.out.screen_type.ScreenTypeRepository;
import com.uos.dsd.cinema.domain.screen_type.ScreenType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenTypeJpaRepository
    extends JpaRepository<ScreenType, String>, ScreenTypeRepository {
}
