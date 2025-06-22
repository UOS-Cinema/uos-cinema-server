package com.uos.dsd.cinema.application.registry;

import com.uos.dsd.cinema.adaptor.out.persistence.affliliate.BankJpaRepository;
import com.uos.dsd.cinema.domain.affiliate.AffiliateExceptionCode;
import com.uos.dsd.cinema.domain.affiliate.Bank;
import com.uos.dsd.cinema.domain.affiliate.BankReloadEvent;
import com.uos.dsd.cinema.common.exception.http.NotFoundException;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

// TODO: 추후 추상화 필요
@Component
@RequiredArgsConstructor
public class BankRegistry extends LookupRegistry<Bank> {

    private final BankJpaRepository bankJpaRepository;

    @Override
    protected RuntimeException notFoundException() {
        return new NotFoundException(AffiliateExceptionCode.AFFILIATE_NOT_FOUND);
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void register() {
        super.register(bankJpaRepository.findAll(), Bank::getName);
    }

    @EventListener
    public void reload(BankReloadEvent event) {

        super.register(bankJpaRepository.findAll(), Bank::getName);
    }
}

