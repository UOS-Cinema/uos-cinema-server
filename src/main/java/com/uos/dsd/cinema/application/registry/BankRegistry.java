package com.uos.dsd.cinema.application.registry;

import com.uos.dsd.cinema.application.port.out.affiliate.BankRepository;
import com.uos.dsd.cinema.domain.affiliate.AffiliateExceptionCode;
import com.uos.dsd.cinema.domain.affiliate.Bank;
import com.uos.dsd.cinema.domain.affiliate.BankReloadEvent;
import com.uos.dsd.cinema.common.exception.http.NotFoundException;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BankRegistry extends LookupRegistry<Bank> {

    private final BankRepository bankRepository;

    @Override
    protected RuntimeException notFoundException() {
        return new NotFoundException(AffiliateExceptionCode.AFFILIATE_NOT_FOUND);
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void register() {
        super.register(bankRepository.findAll(), Bank::getName);
    }

    @EventListener
    public void reload(BankReloadEvent event) {

        super.register(bankRepository.findAll(), Bank::getName);
    }
}

