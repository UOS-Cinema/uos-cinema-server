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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class ScreenTypeRegistry {

    private final ScreenTypeRepository screenTypeRepository;

    private final Map<String, ScreenType> screenTypeMap = new HashMap<>();

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void register() {

        List<ScreenType> screenTypes = screenTypeRepository.findAll();
        screenTypes.forEach(screenType -> {
            screenTypeMap.put(screenType.getType(), screenType);
        });
    }

    @EventListener
    public void reload(ScreenTypeReloadEvent event) {

        screenTypeMap.clear();
        register();
    }

    public ScreenType get(String type) {

        ScreenType screenType = screenTypeMap.get(type);
        if (screenType == null) {
            throw new NotFoundException(ScreenTypeExceptionCode.SCREEN_TYPE_NOT_FOUND);
        }
        return screenType;
    }

    public List<ScreenType> getAll() {

        return new ArrayList<>(screenTypeMap.values());
    }
}

