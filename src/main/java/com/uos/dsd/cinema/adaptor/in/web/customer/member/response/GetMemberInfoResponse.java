package com.uos.dsd.cinema.adaptor.in.web.customer.member.response;

import java.time.LocalDate;

public record GetMemberInfoResponse(
    String username,
    String name,
    String phone,
    LocalDate birthDate,
    String profileImageUrl
) { } 
