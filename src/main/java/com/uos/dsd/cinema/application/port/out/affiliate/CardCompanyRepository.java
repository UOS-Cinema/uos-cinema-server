package com.uos.dsd.cinema.application.port.out.affiliate;

import com.uos.dsd.cinema.domain.affiliate.CardCompany;

import java.util.List;

public interface CardCompanyRepository {

    CardCompany save(CardCompany cardCompany);

    List<CardCompany> findAll();
}
