package com.uos.dsd.cinema.application.port.in.customer.guest.command;

import java.time.LocalDate;

public record LoginGuestCommand(
    String name,
    String phone,
    LocalDate birthDate,
    String password
) { }
