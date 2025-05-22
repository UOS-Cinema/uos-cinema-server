package com.uos.dsd.cinema.application.port.in.admin.usecase;

import com.uos.dsd.cinema.application.port.in.admin.command.SignupAdminCommand;

public interface SignupAdminUsecase {
    Long signup(SignupAdminCommand command);
}
