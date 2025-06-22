package com.uos.dsd.cinema.adaptor.out.persistence.affliliate;

import com.uos.dsd.cinema.domain.affiliate.CardCompany;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CardCompanyJpaRepository
            extends JpaRepository<CardCompany, String> {
}
