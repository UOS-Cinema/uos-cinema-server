package com.uos.dsd.cinema.application.port.in.customer.member.usecase;

import com.uos.dsd.cinema.application.port.in.customer.member.command.DeleteMemberCommand;

public interface DeleteMemberUsecase {
    
    void deleteMember(DeleteMemberCommand command);
} 
