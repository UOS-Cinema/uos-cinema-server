package com.uos.dsd.cinema.domain.customer_type;

import com.uos.dsd.cinema.domain.price.constaint.PriceConstraint;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "customer_types")
@Getter
@ToString
@EqualsAndHashCode(of = "type")
@NoArgsConstructor
public class CustomerType {

    @Id
    private String type;

    private int discountAmount;

    public CustomerType(String type, int discountAmount) {

        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("type is required");
        }
        this.type = type;
        PriceConstraint.validateDiscountAmount(discountAmount);
        this.discountAmount = discountAmount;
    }

    public void modifyDiscountAmount(int discountAmount) {

        PriceConstraint.validateDiscountAmount(discountAmount);
        this.discountAmount = discountAmount;
    }
}
