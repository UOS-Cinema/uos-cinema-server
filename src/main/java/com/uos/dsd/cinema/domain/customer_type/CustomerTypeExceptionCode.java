package com.uos.dsd.cinema.domain.customer_type;

import com.uos.dsd.cinema.common.exception.code.ResultCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomerTypeExceptionCode implements ResultCode {

    CUSTOMER_TYPE_NOT_FOUND("CST001", "Customer type not found"),
    ;

    private final String code;
    private final String message;
}
