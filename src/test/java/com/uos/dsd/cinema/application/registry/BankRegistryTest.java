package com.uos.dsd.cinema.application.registry;

import static org.assertj.core.api.Assertions.assertThat;

import com.uos.dsd.cinema.integration.IntegrationTest;
import com.uos.dsd.cinema.domain.affiliate.Bank;
import com.uos.dsd.cinema.domain.affiliate.BankReloadEvent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.util.List;

public class BankRegistryTest extends IntegrationTest {

    @AfterEach
    public void reloadBankRegistry() {

        bankRegistry.reload(new BankReloadEvent());
    }

    @Test
    public void bankRegistryInitTest() {

        // when
        List<Bank> banks = bankRegistry.getAll();
        log.info("banks: {}", banks);

        // then
        assertThat(banks.size()).isGreaterThan(0);
    }

    @Test
    public void bankRegistryGetTest() {

        // when
        Bank bank = bankRegistry.get("국민은행");

        // then
        assertThat(bank.getName()).isEqualTo("국민은행");
    }

    @Test
    public void bankRegistryReloadTestWhenAdded() throws InterruptedException {

        // given
        int size = bankRegistry.getAll().size();
        Bank bank = new Bank("NEW", null, 0);
        bankRepository.save(bank);

        // when
        BankReloadEvent event = new BankReloadEvent();
        eventPublisher.publishEvent(event);

        Thread.sleep(100);

        // then
        List<Bank> banks = bankRegistry.getAll();
        log.info("banks: {}", banks);
        assertThat(banks.size()).isEqualTo(size + 1);
    }
}
