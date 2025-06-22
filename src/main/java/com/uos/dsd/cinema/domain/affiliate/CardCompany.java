package com.uos.dsd.cinema.domain.affiliate;

import com.uos.dsd.cinema.domain.price.constaint.PriceConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "card_companies")
@Getter
@ToString
@EqualsAndHashCode(of = "name")
@NoArgsConstructor
public class CardCompany {

    @Id
    private String name;

    private String logoUrl;

    private int discountAmount;

    public CardCompany(String name, String logoUrl, int discountAmount) {

        AffiliateConstraint.validateName(name);
        this.name = name;
        AffiliateConstraint.validateLogoUrl(logoUrl);
        this.logoUrl = logoUrl;
        PriceConstraint.validateDiscountAmount(discountAmount);
        this.discountAmount = discountAmount;
    }

    public void modifyLogoUrl(String logoUrl) {

        AffiliateConstraint.validateLogoUrl(logoUrl);
        this.logoUrl = logoUrl;
    }

    public void modifyDiscountAmount(int discountAmount) {

        PriceConstraint.validateDiscountAmount(discountAmount);
        this.discountAmount = discountAmount;
    }
}
