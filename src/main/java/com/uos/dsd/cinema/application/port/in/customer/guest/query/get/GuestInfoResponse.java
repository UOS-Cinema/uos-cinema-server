package com.uos.dsd.cinema.application.port.in.customer.guest.query.get;

import java.time.LocalDate;

import com.uos.dsd.cinema.domain.customer.guest.Guest;

public record GuestInfoResponse(
    String name,
    String phone,
    LocalDate birthDate
) {
    public static GuestInfoResponse from(Guest guest) {
        return new GuestInfoResponse(guest.getName(), guest.getPhone(), guest.getBirthDate());
    }
}
