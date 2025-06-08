package com.uos.dsd.cinema.adaptor.in.web.customer.guest.request;

import java.time.LocalDate;

public record GuestLoginRequest(
    String name,
    String phone,
    LocalDate birthDate,
    String password
) { }
