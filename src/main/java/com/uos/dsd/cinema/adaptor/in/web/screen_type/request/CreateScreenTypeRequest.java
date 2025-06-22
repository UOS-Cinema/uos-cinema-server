package com.uos.dsd.cinema.adaptor.in.web.screen_type.request;

public record CreateScreenTypeRequest(
    String type,
    String iconUrl,
    int price
) {

}
