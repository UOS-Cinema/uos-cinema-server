package com.uos.dsd.cinema.application.port.in.guest.usecase;

import com.uos.dsd.cinema.application.port.in.guest.command.LoginGuestCommand;

public interface LoginGuestUsecase {
    
    Long login(LoginGuestCommand command);
}
