package com.uos.dsd.cinema.application.port.in.customer.guest.query.get;

import com.uos.dsd.cinema.domain.customer.guest.Guest;

import java.time.LocalDate;

public record GuestInfoResponse(
    String name,
    String phone,
    LocalDate birthDate
) {
    public static GuestInfoResponse from(Guest guest) {
        return new GuestInfoResponse(guest.getName(), guest.getPhone(), guest.getBirthDate());
    }
}
