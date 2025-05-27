package com.uos.dsd.cinema.application.port.in.guest.usecase;

import com.uos.dsd.cinema.application.port.in.guest.command.GetGuestInfoCommand;
import com.uos.dsd.cinema.application.port.in.guest.response.GuestInfo;

public interface GetGuestInfoUsecase {
    
    GuestInfo getGuestInfo(GetGuestInfoCommand command);
} 
