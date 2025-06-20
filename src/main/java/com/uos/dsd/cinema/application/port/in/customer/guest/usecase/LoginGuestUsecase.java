package com.uos.dsd.cinema.application.port.in.customer.guest.usecase;

import com.uos.dsd.cinema.application.port.in.customer.guest.command.LoginGuestCommand;

public interface LoginGuestUsecase {
    
    Long login(LoginGuestCommand command);
}
