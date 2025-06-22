package com.uos.dsd.cinema.application.port.out.customer.member;

import com.uos.dsd.cinema.domain.customer.member.Member;

import java.util.Optional;

public interface MemberRepository {
    
    Member save(Member member);
    
    Optional<Member> findByUsernameAndDeletedAtIsNull(String username);
    
    Optional<Member> findByIdAndDeletedAtIsNull(Long id);
    
    boolean existsByUsernameAndDeletedAtIsNull(String username);
    
    boolean existsByPhoneAndDeletedAtIsNull(String phone);
} 
