package com.uos.dsd.cinema.application.registry;

import static org.assertj.core.api.Assertions.assertThat;

import com.uos.dsd.cinema.integration.IntegrationTest;
import com.uos.dsd.cinema.domain.screen_type.ScreenType;
import com.uos.dsd.cinema.domain.screen_type.ScreenTypeReloadEvent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.util.List;

public class ScreenTypeRegistryTest extends IntegrationTest {

    @AfterEach
    public void reloadScreenTypeRegistry() {

        screenTypeRegistry.reload(new ScreenTypeReloadEvent());
    }

    @Test
    public void screenTypeRegistryInitTest() {

        // when
        List<ScreenType> screenTypes = screenTypeRegistry.getAll();
        log.info("screenTypes: {}", screenTypes);

        // then
        assertThat(screenTypes.size()).isGreaterThan(0);
    }

    @Test
    public void screenTypeRegistryGetTest() {

        // when
        ScreenType screenType = screenTypeRegistry.get("2D");

        // then
        assertThat(screenType.getType()).isEqualTo("2D");
    }

    @Test
    public void screenTypeRegistryReloadTestWhenAdded() throws InterruptedException {

        // given
        int size = screenTypeRegistry.getAll().size();
        ScreenType screenType = new ScreenType("IMAX", null, 15000);
        screenTypeRepository.save(screenType);

        // when
        ScreenTypeReloadEvent event = new ScreenTypeReloadEvent();
        eventPublisher.publishEvent(event);

        Thread.sleep(100);

        // then
        List<ScreenType> screenTypes = screenTypeRegistry.getAll();
        log.info("screenTypes: {}", screenTypes);
        assertThat(screenTypes.size()).isEqualTo(size + 1);
    }
}
