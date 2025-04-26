package com.uos.dsd.cinema.adaptor.out.persistence;

import com.uos.dsd.cinema.application.port.out.repository.GuestRepository;
import com.uos.dsd.cinema.domain.model.user.Guest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaGuestRepository implements GuestRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Guest> findById(Long id) {
        Guest guest = entityManager.find(Guest.class, id);
        return Optional.ofNullable(guest);
    }

    @Override
    public List<Guest> findByNameAndPhoneAndBirthDate(String name, String phone, LocalDate birthDate) {
        try {
            return entityManager.createQuery(
                    "SELECT g FROM Guest g WHERE g.name = :name AND g.phone = :phone AND g.birthDate = :birthDate",
                    Guest.class)
                    .setParameter("name", name)
                    .setParameter("phone", phone)
                    .setParameter("birthDate", birthDate)
                    .getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public Long save(Guest guest) {
        entityManager.persist(guest);
        return guest.getCustomerId();
    }
}
