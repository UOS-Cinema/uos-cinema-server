package com.uos.dsd.cinema.domain.price.exception;

import com.uos.dsd.cinema.common.exception.code.ResultCode;
import com.uos.dsd.cinema.domain.price.constaint.PriceConstraint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PriceExceptionCode implements ResultCode {
    
    INVALID_PRICE("PRI001", String.format(
        "Price must be between %d and %d", 
        PriceConstraint.MIN_PRICE, 
        PriceConstraint.MAX_PRICE)),
    INVALID_DISCOUNT_AMOUNT("PRI002", String.format(
        "Discount amount must be between %d and %d", 
        0, 
        PriceConstraint.MAX_DISCOUNT_AMOUNT)),
    ;

    private final String code;
    private final String message;
}
