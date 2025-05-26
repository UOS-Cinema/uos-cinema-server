package com.uos.dsd.cinema.application.port.in.guest.usecase;

import com.uos.dsd.cinema.application.port.in.guest.command.LoginGuestCommand;
import com.uos.dsd.cinema.domain.guest.Guest;

public interface LoginGuestUsecase {
    
    Guest login(LoginGuestCommand command);
}
