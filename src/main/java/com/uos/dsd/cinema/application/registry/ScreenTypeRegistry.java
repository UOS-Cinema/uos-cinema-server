package com.uos.dsd.cinema.application.registry;

import com.uos.dsd.cinema.application.port.out.screen_type.ScreenTypeRepository;
import com.uos.dsd.cinema.domain.screen_type.ScreenType;
import com.uos.dsd.cinema.domain.screen_type.ScreenTypeReloadEvent;
import com.uos.dsd.cinema.domain.screen_type.exception.ScreenTypeExceptionCode;
import com.uos.dsd.cinema.common.exception.http.NotFoundException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScreenTypeRegistry extends LookupRegistry<ScreenType> {

    private final ScreenTypeRepository screenTypeRepository;

    @Override
    protected RuntimeException notFoundException() {

        return new NotFoundException(ScreenTypeExceptionCode.SCREEN_TYPE_NOT_FOUND);
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void register() {

        super.register(screenTypeRepository.findAll(), ScreenType::getType);
    }

    @EventListener
    public void reload(ScreenTypeReloadEvent event) {

        super.register(screenTypeRepository.findAll(), ScreenType::getType);
    }
}

