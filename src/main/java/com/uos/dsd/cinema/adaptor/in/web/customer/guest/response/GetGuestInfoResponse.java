package com.uos.dsd.cinema.adaptor.in.web.customer.guest.response;

import java.time.LocalDate;

public record GetGuestInfoResponse(
    String name,
    String phone,
    LocalDate birthDate
) { } 
