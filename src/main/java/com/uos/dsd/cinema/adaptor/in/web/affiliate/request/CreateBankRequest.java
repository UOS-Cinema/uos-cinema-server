package com.uos.dsd.cinema.adaptor.in.web.affiliate.request;

public record CreateBankRequest(
    String name,
    String logoUrl,
    int discountAmount
) {

}
