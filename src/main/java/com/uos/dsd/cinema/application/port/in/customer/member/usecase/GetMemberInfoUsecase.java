package com.uos.dsd.cinema.application.port.in.customer.member.usecase;

import com.uos.dsd.cinema.application.port.in.customer.member.query.GetMemberInfoQuery;
import com.uos.dsd.cinema.application.port.in.customer.member.response.MemberInfoResponse;

public interface GetMemberInfoUsecase {
    
    MemberInfoResponse getMemberInfo(GetMemberInfoQuery query);
} 
