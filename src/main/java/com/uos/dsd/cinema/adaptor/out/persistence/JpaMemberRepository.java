package com.uos.dsd.cinema.adaptor.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.uos.dsd.cinema.application.port.out.repository.MemberRepository;
import com.uos.dsd.cinema.domain.model.user.Member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaMemberRepository implements MemberRepository {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Member> findByMemberId(String memberId) {
        try {
            Member member = entityManager.createQuery(
                    "SELECT m FROM Member m WHERE m.memberId = :memberId AND m.deletedAt IS NULL",
                    Member.class)
                    .setParameter("memberId", memberId)
                    .getSingleResult();
            return Optional.of(member);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Long save(Member member) {
        entityManager.persist(member);
        return member.getCustomerId();
    }
} 
