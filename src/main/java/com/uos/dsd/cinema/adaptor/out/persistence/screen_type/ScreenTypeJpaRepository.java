package com.uos.dsd.cinema.adaptor.out.persistence.screen_type;

import com.uos.dsd.cinema.application.port.out.screen_type.ScreenTypeRepository;
import com.uos.dsd.cinema.domain.screen_type.ScreenType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ScreenTypeJpaRepository
        extends JpaRepository<ScreenType, String>, ScreenTypeRepository {
        
    @Query(value = "SELECT COUNT(s) FROM theater_screen_types t WHERE t.screen_type = :type",
            nativeQuery = true)
    int countFromTheater(String type);

    @Query(value = "SELECT COUNT(s) FROM movie_screen_types t WHERE t.screen_type = :type",
            nativeQuery = true)
    int countFromMovie(String type);
}
