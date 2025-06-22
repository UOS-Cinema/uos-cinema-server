package com.uos.dsd.cinema.adaptor.in.web.customer_type.request;

public record CreateCustomerTypeRequest(
    String type,
    int discountAmount
) {

}
