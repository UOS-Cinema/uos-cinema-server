package com.uos.dsd.cinema.adaptor.in.web.guest.request;

import java.time.LocalDate;

public record GuestLoginRequest(
    String name,
    String phone,
    LocalDate birthDate,
    String password
) { }
