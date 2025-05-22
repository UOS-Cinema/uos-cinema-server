package com.uos.dsd.cinema.application.port.in.admin.usecase;

import com.uos.dsd.cinema.application.port.in.admin.command.DeleteAdminCommand;

public interface DeleteAdminUsecase {
    void delete(DeleteAdminCommand command);
}
