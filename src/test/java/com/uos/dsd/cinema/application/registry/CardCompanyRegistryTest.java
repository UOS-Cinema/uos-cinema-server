package com.uos.dsd.cinema.application.registry;

import static org.assertj.core.api.Assertions.assertThat;

import com.uos.dsd.cinema.integration.IntegrationTest;
import com.uos.dsd.cinema.domain.affiliate.CardCompany;
import com.uos.dsd.cinema.domain.affiliate.CardCompanyReloadEvent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.util.List;

public class CardCompanyRegistryTest extends IntegrationTest {

    @AfterEach
    public void reloadCardCompanyRegistry() {

        cardCompanyRegistry.reload(new CardCompanyReloadEvent());
    }

    @Test
    public void cardCompanyRegistryInitTest() {

        // when
        List<CardCompany> cardCompanies = cardCompanyRegistry.getAll();
        log.info("cardCompanies: {}", cardCompanies);

        // then
        assertThat(cardCompanies.size()).isGreaterThan(0);
    }

    @Test
    public void cardCompanyRegistryGetTest() {

        // when
        CardCompany cardCompany = cardCompanyRegistry.get("하나카드");

        // then
        assertThat(cardCompany.getName()).isEqualTo("하나카드");
    }

    @Test
    public void bankRegistryReloadTestWhenAdded() throws InterruptedException {

        // given
        int size = cardCompanyRegistry.getAll().size();
        CardCompany cardCompany = new CardCompany("NEW", null, 0);
        cardCompanyRepository.save(cardCompany);

        // when
        CardCompanyReloadEvent event = new CardCompanyReloadEvent();
        eventPublisher.publishEvent(event);

        Thread.sleep(100);

        // then
        List<CardCompany> cardCompanies = cardCompanyRegistry.getAll();
        log.info("cardCompanies: {}", cardCompanies);
        assertThat(cardCompanies.size()).isEqualTo(size + 1);
    }
}
