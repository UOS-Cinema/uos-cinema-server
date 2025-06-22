package com.uos.dsd.cinema.application.port.in.customer.member.usecase;

import com.uos.dsd.cinema.application.port.in.customer.member.command.SignupMemberCommand;

public interface SignupMemberUsecase {
    
    Long signup(SignupMemberCommand command);
} 
