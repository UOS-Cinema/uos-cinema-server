package com.uos.dsd.cinema.application.port.in.admin.usecase;

import com.uos.dsd.cinema.application.port.in.admin.command.AdminDeleteCommand;

public interface AdminDeleteUsecase {
    void delete(AdminDeleteCommand command);
}
