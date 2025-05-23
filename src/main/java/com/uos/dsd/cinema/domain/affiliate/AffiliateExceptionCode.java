package com.uos.dsd.cinema.domain.affiliate;

import com.uos.dsd.cinema.common.exception.code.ResultCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AffiliateExceptionCode implements ResultCode {

    AFFILIATE_NOT_FOUND("AFF001", "Affiliate not found"),
    AFFILIATE_INVALID_NAME("AFF002", "Affiliate name must be alphanumeric"),
    AFFILIATE_INVALID_NAME_LENGTH("AFF003", String.format(
        "Affiliate name must be less than %d characters", 
        AffiliateConstraint.NAME_MAX_LENGTH)),
    AFFILIATE_INVALID_LOGO_URL_LENGTH("AFF004", String.format(
        "Affiliate logo URL must be less than %d characters", 
        AffiliateConstraint.LOGO_URL_MAX_LENGTH)),
    ;

    private final String code;
    private final String message;
}
