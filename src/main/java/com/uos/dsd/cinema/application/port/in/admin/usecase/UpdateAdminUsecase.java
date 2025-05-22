package com.uos.dsd.cinema.application.port.in.admin.usecase;

import com.uos.dsd.cinema.application.port.in.admin.command.UpdateAdminCommand;

public interface UpdateAdminUsecase {
    void update(UpdateAdminCommand command);
}
