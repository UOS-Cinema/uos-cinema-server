package com.uos.dsd.cinema.application.port.in.customer.member.response;

import com.uos.dsd.cinema.domain.customer.member.Member;

import java.time.LocalDate;

public record MemberInfoResponse(
    String username,
    String name,
    String phone,
    LocalDate birthDate,
    String profileImageUrl
) {
    public static MemberInfoResponse from(Member member) {
        return new MemberInfoResponse(
            member.getUsername(),
            member.getName(),
            member.getPhone(),
            member.getBirthDate(),
            member.getProfileImageUrl()
        );
    }
} 
