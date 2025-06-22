package com.uos.dsd.cinema.adaptor.in.web.affilliate.request;

public record CreateBankRequest(
    String name,
    String logoUrl,
    int discountAmount
) {

}
