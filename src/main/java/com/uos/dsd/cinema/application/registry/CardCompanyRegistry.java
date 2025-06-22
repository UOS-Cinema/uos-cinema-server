package com.uos.dsd.cinema.application.registry;

import com.uos.dsd.cinema.adaptor.out.persistence.affliliate.CardCompanyJpaRepository;
import com.uos.dsd.cinema.domain.affiliate.AffiliateExceptionCode;
import com.uos.dsd.cinema.domain.affiliate.CardCompany;
import com.uos.dsd.cinema.domain.affiliate.CardCompanyReloadEvent;
import com.uos.dsd.cinema.common.exception.http.NotFoundException;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CardCompanyRegistry extends LookupRegistry<CardCompany> {

    private final CardCompanyJpaRepository cardCompanyRepository;

    @Override
    protected RuntimeException notFoundException() {
        return new NotFoundException(AffiliateExceptionCode.AFFILIATE_NOT_FOUND);
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void register() {
        super.register(cardCompanyRepository.findAll(), CardCompany::getName);
    }

    @EventListener
    public void reload(CardCompanyReloadEvent event) {

        super.register(cardCompanyRepository.findAll(), CardCompany::getName);
    }
}

