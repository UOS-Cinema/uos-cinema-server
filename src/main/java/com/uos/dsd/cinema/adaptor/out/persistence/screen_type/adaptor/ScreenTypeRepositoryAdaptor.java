package com.uos.dsd.cinema.adaptor.out.persistence.screen_type.adaptor;

import com.uos.dsd.cinema.application.port.out.screen_type.ScreenTypeRepository;
import com.uos.dsd.cinema.adaptor.out.persistence.screen_type.jpa.ScreenTypeJPARepository;
import com.uos.dsd.cinema.domain.screen_type.ScreenType;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ScreenTypeRepositoryAdaptor implements ScreenTypeRepository {

    private final ScreenTypeJPARepository screenTypeJPARepository;

    @Override
    public ScreenType save(ScreenType screenType) {

        return screenTypeJPARepository.save(screenType);
    }

    @Override
    public List<ScreenType> findAll() {

        return screenTypeJPARepository.findAll();
    }

    @Override
    public void deleteById(String type) {

        screenTypeJPARepository.deleteById(type);
    }
}
