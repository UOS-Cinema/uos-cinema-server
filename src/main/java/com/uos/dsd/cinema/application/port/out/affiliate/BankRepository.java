package com.uos.dsd.cinema.application.port.out.affiliate;

import com.uos.dsd.cinema.domain.affiliate.Bank;

import java.util.List;

public interface BankRepository {

    Bank save(Bank bank);

    List<Bank> findAll();

    void deleteById(String name);
}
