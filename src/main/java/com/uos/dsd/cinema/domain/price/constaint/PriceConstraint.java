package com.uos.dsd.cinema.domain.price.constaint;

import com.uos.dsd.cinema.domain.price.exception.PriceExceptionCode;

public class PriceConstraint {

    public static final int MIN_PRICE = 0;
    public static final int MAX_PRICE = 1000000;

    public static final int MAX_DISCOUNT_AMOUNT = 10000;

    public static void validatePrice(int price) {

        if (price < MIN_PRICE || price > MAX_PRICE) {
            throw new IllegalArgumentException(PriceExceptionCode.INVALID_PRICE.getMessage());
        }
    }

    public static void validateDiscountAmount(int discountAmount) {

        if (discountAmount < 0 || discountAmount > MAX_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException(PriceExceptionCode.INVALID_DISCOUNT_AMOUNT.getMessage());
        }
    }
}
