package com.uos.dsd.cinema.domain.affiliate;

public class AffiliateConstraint {

    public static final String NAME_REGEX = "^[a-zA-Z0-9]+$";
    public static final int NAME_MAX_LENGTH = 20;
    public static final int LOGO_URL_MAX_LENGTH = 255;

    public static void validateName(String name) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    AffiliateExceptionCode.AFFILIATE_INVALID_NAME.getMessage());
        }
        if (name.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    AffiliateExceptionCode.AFFILIATE_INVALID_NAME_LENGTH.getMessage());
        }
    }

    public static void validateLogoUrl(String logoUrl) {

        if (logoUrl != null && logoUrl.length() > LOGO_URL_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    AffiliateExceptionCode.AFFILIATE_INVALID_LOGO_URL_LENGTH.getMessage());
        }
    }



}
