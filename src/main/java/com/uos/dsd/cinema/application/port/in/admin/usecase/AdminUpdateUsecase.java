package com.uos.dsd.cinema.application.port.in.admin.usecase;

import com.uos.dsd.cinema.application.port.in.admin.command.AdminUpdateCommand;

public interface AdminUpdateUsecase {
    void update(AdminUpdateCommand command);
}
