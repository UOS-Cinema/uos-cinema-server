package com.uos.dsd.cinema.adaptor.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.uos.dsd.cinema.application.port.out.repository.AdminRepository;
import com.uos.dsd.cinema.domain.model.user.Admin;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaAdminRepository implements AdminRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public Optional<Admin> findByName(String name) {
        try {
            Admin admin = entityManager.createQuery(
                    "SELECT a FROM Admin a WHERE a.name = :name AND a.deletedAt IS NULL", 
                    Admin.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.of(admin);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Long save(Admin admin) {
        entityManager.persist(admin);
        return admin.getId();
    }
} 
