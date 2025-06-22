package com.uos.dsd.cinema.adaptor.in.web.affilliate.request;

public record CreateCardCompanyRequest(
    String name,
    String logoUrl,
    int discountAmount
) {

}
