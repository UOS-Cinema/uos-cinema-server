package com.uos.dsd.cinema.application.service.customer.guest.command;

import com.uos.dsd.cinema.application.port.in.customer.guest.command.login.LoginGuestCommand;
import com.uos.dsd.cinema.application.port.in.customer.guest.command.login.LoginGuestUsecase;
import com.uos.dsd.cinema.application.port.out.customer.guest.command.GuestCommandRepository;
import com.uos.dsd.cinema.domain.customer.guest.Guest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestCommandService implements LoginGuestUsecase {
    
    private final GuestCommandRepository guestRepository;
    
    @Transactional
    @Override
    public Long login(LoginGuestCommand command) {

        // Guest 존재 시 반환
        List<Guest> guests = guestRepository.findAllByNameAndPhoneAndBirthDate(
            command.name(),
            command.phone(),
            command.birthDate()
        );
        Guest foundGuest = guests.stream()
            .filter(guest -> guest.isPasswordMatched(command.password()))
            .findFirst()
            .orElse(null);
        if (foundGuest != null) {
            return foundGuest.getId();
        }

        // Guest 미존재 시, 새로 생성 후 반환
        Guest newGuest = new Guest(
            command.name(),
            command.phone(),
            command.birthDate(),
            command.password()
        );
        return guestRepository.save(newGuest).getId();
    }
    
    @Transactional
    public void deleteGuest(Guest guest) {
        
        guestRepository.delete(guest);
    }
} 
