package com.uos.dsd.cinema.adaptor.out.persistence.customer.member;

import com.uos.dsd.cinema.application.port.out.customer.member.MemberRepository;
import com.uos.dsd.cinema.domain.customer.member.Member;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberJpaRepository extends CrudRepository<Member, Long>, MemberRepository {

    @Override
    Member save(Member member);
    
    @Override
    Optional<Member> findByUsernameAndDeletedAtIsNull(String username);
    
    @Override
    Optional<Member> findByIdAndDeletedAtIsNull(Long id);
    
    @Override
    boolean existsByUsernameAndDeletedAtIsNull(String username);
    
    @Override
    boolean existsByPhoneAndDeletedAtIsNull(String phone);
} 
