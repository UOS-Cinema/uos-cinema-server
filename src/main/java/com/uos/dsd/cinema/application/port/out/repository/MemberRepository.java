package com.uos.dsd.cinema.application.port.out.repository;

import java.util.Optional;

import com.uos.dsd.cinema.domain.model.user.Member;

public interface MemberRepository {
    Optional<Member> findByMemberId(String memberId);
    Long save(Member member);
} 
