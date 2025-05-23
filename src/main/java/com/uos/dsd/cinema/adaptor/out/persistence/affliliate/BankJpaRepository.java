package com.uos.dsd.cinema.adaptor.out.persistence.affliliate;

import com.uos.dsd.cinema.application.port.out.affiliate.BankRepository;
import com.uos.dsd.cinema.domain.affiliate.Bank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankJpaRepository extends JpaRepository<Bank, String>, BankRepository {
}
