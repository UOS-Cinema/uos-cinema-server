package com.uos.dsd.cinema.domain.screen_type;

import com.uos.dsd.cinema.domain.screen_type.constaint.PriceConstraint;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "screen_types")
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ScreenType {

    @Id
    private String type;

    private String iconUrl;

    private int price;

    public ScreenType(
            String type, 
            String iconUrl, 
            int price) {

        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Type is required");
        }
        PriceConstraint.validatePrice(price);

        this.type = type;
        this.iconUrl = iconUrl;
        this.price = price;
    }
}
