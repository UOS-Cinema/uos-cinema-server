package com.uos.dsd.cinema.domain.screen_type.constaint;

public class PriceConstraint {

    public static final int MIN_PRICE = 0;
    public static final int MAX_PRICE = 1000000;

    public static void validatePrice(int price) {

        if (price < MIN_PRICE || price > MAX_PRICE) {
            throw new IllegalArgumentException("Price must be between " + MIN_PRICE + " and " + MAX_PRICE);
        }
    }
}
