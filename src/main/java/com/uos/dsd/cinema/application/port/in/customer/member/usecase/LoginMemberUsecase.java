package com.uos.dsd.cinema.application.port.in.customer.member.usecase;

import com.uos.dsd.cinema.application.port.in.customer.member.command.LoginMemberCommand;

public interface LoginMemberUsecase {
    
    Long login(LoginMemberCommand command);
} 
