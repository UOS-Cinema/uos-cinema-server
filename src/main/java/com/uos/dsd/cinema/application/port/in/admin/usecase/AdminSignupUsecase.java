package com.uos.dsd.cinema.application.port.in.admin.usecase;

import com.uos.dsd.cinema.application.port.in.admin.command.AdminSignupCommand;

public interface AdminSignupUsecase {
    Long signup(AdminSignupCommand command);
}
