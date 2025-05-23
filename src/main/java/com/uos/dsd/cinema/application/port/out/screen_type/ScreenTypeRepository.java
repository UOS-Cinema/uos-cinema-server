package com.uos.dsd.cinema.application.port.out.screen_type;

import com.uos.dsd.cinema.domain.screen_type.ScreenType;

import java.util.List;

public interface ScreenTypeRepository {

    ScreenType save(ScreenType screenType);

    List<ScreenType> findAll();

    void deleteById(String type);
}
