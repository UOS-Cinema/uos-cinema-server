package com.uos.dsd.cinema.application.port.in.customer.member.usecase;

import com.uos.dsd.cinema.application.port.in.customer.member.command.UpdateMemberCommand;

public interface UpdateMemberUsecase {
    
    void updateMember(UpdateMemberCommand command);
} 
