package com.uos.dsd.cinema.adaptor.in.web.affiliate.request;

public record CreateCardCompanyRequest(
    String name,
    String logoUrl,
    int discountAmount
) {

}
