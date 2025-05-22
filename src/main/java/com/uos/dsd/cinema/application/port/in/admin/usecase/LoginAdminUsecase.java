package com.uos.dsd.cinema.application.port.in.admin.usecase;

import com.uos.dsd.cinema.application.port.in.admin.command.LoginAdminCommand;

public interface LoginAdminUsecase {
    Long login(LoginAdminCommand command);
}
