package com.uos.dsd.cinema.application.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uos.dsd.cinema.application.port.out.repository.AdminRepository;
import com.uos.dsd.cinema.application.port.out.repository.CustomerRepository;
import com.uos.dsd.cinema.application.port.out.repository.GuestRepository;
import com.uos.dsd.cinema.application.port.out.repository.MemberRepository;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.exception.http.BadRequestException;
import com.uos.dsd.cinema.common.exception.http.UnauthorizedException;
import com.uos.dsd.cinema.domain.model.user.Admin;
import com.uos.dsd.cinema.domain.model.user.Customer.UserType;
import com.uos.dsd.cinema.domain.model.user.Guest;
import com.uos.dsd.cinema.domain.model.user.Member;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    private final AdminRepository adminRepository;
    private final GuestRepository guestRepository;
    private final MemberRepository memberRepository;
    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    public Long registerAdmin(String name, String password) {
        if (adminRepository.findByName(name).isPresent()) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST);
        }

        Admin admin = Admin.builder()
                .name(name)
                .password(passwordEncoder.encode(password))
                .build();
        return adminRepository.save(admin);
    }

    private Long registerGuest(String name, String phone, LocalDate birthDate, String password) {
        Guest guest = Guest.builder()
                .name(name)
                .phone(phone)
                .birthDate(birthDate)
                .password(passwordEncoder.encode(password))
                .build();
        
        Long customerId = customerRepository.save(UserType.GUEST);
        guest.setCustomerId(customerId);
        guestRepository.save(guest);
        return customerId;
    }

    public Long registerMember(String memberId, String password, String name, String phone, LocalDate birthDate,
            String profileImage) {

        if (memberRepository.findByMemberId(memberId).isPresent()) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST);
        }

        Member member = Member.builder()
                .memberId(memberId)
                .password(passwordEncoder.encode(password))
                .name(name)
                .phone(phone)
                .birthDate(birthDate)
                .profileImage(profileImage)
                .build();

        Long customerId = customerRepository.save(UserType.MEMBER);
        member.setCustomerId(customerId);
        memberRepository.save(member);
        return customerId;
    }

    public Member loginMember(String memberId, String password) {
        Optional<Member> member = memberRepository.findByMemberId(memberId);
        if (member.isEmpty() || !passwordEncoder.matches(password, member.get().getPassword())) {
            throw new UnauthorizedException(CommonResultCode.UNAUTHORIZED);
        }
        return member.get();
    }

    public Guest loginGuest(String name, String phone, LocalDate birthDate, String password) {
        List<Guest> guests = guestRepository.findByNameAndPhoneAndBirthDate(name, phone, birthDate);
        for (Guest guest : guests) {
            if (passwordEncoder.matches(password, guest.getPassword())) {
                return guest;
            }
        }
        
        // 게스트 등록
        Long guestId = registerGuest(name, phone, birthDate, password);
        Optional<Guest> newGuest = guestRepository.findById(guestId);
        if (newGuest.isEmpty()) {
            throw new UnauthorizedException(CommonResultCode.UNAUTHORIZED);
        }
        return newGuest.get();
    }

    public Admin loginAdmin(String name, String password) {
        Optional<Admin> admin = adminRepository.findByName(name);
        if (admin.isEmpty() || !passwordEncoder.matches(password, admin.get().getPassword())) {
            throw new UnauthorizedException(CommonResultCode.UNAUTHORIZED);
        }
        return admin.get();
    }
}
