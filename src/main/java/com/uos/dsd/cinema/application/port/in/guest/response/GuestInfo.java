package com.uos.dsd.cinema.application.port.in.guest.response;

import java.time.LocalDate;

import com.uos.dsd.cinema.domain.guest.Guest;

public record GuestInfo(
    String name,
    String phone,
    LocalDate birthDate
) {
    public static GuestInfo from(Guest guest) {
        return new GuestInfo(guest.getName(), guest.getPhone(), guest.getBirthDate());
    }
}
