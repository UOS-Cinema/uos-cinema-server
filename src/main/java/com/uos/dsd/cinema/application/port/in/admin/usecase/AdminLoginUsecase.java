package com.uos.dsd.cinema.application.port.in.admin.usecase;

import com.uos.dsd.cinema.application.port.in.admin.command.AdminLoginCommand;

public interface AdminLoginUsecase {
    Long login(AdminLoginCommand command);
}
