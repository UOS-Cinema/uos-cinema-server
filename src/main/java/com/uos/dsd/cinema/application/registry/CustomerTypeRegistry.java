package com.uos.dsd.cinema.application.registry;

import com.uos.dsd.cinema.adaptor.out.persistence.customer_type.CustomerTypeJpaRepository;
import com.uos.dsd.cinema.domain.customer_type.CustomerType;
import com.uos.dsd.cinema.domain.customer_type.CustomerTypeExceptionCode;
import com.uos.dsd.cinema.domain.customer_type.CustomerTypeReloadEvent;
import com.uos.dsd.cinema.common.exception.http.NotFoundException;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerTypeRegistry extends LookupRegistry<CustomerType> {

    private final CustomerTypeJpaRepository customerTypeRepository;

    @Override
    protected RuntimeException notFoundException() {
        return new NotFoundException(CustomerTypeExceptionCode.CUSTOMER_TYPE_NOT_FOUND);
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void register() {
        super.register(customerTypeRepository.findAll(), CustomerType::getType);
    }

    @EventListener
    public void reload(CustomerTypeReloadEvent event) {

        super.register(customerTypeRepository.findAll(), CustomerType::getType);
    }
}

