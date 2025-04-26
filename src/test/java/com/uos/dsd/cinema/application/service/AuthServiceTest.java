package com.uos.dsd.cinema.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.uos.dsd.cinema.common.exception.http.BadRequestException;
import com.uos.dsd.cinema.common.exception.http.UnauthorizedException;
import com.uos.dsd.cinema.domain.model.user.Admin;
import com.uos.dsd.cinema.domain.model.user.Guest;
import com.uos.dsd.cinema.domain.model.user.Member;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    // 관리자 로그인 테스트
    @Test
    void testLoginAdmin_Success() {
        // given
        String adminName = "testAdmin";
        String password = "password123";
        
        // 관리자 등록
        Long adminId = authService.registerAdmin(adminName, password);
        assertNotNull(adminId);
        
        // when
        Admin admin = authService.loginAdmin(adminName, password);
        
        // then
        assertNotNull(admin);
        assertEquals(adminName, admin.getName());
    }
    
    @Test
    void testLoginAdmin_FailWithWrongPassword() {
        // given
        String adminName = "testAdmin";
        String password = "password123";
        
        // 관리자 등록
        Long adminId = authService.registerAdmin(adminName, password);
        assertNotNull(adminId);
        
        // when & then
        assertThrows(UnauthorizedException.class, () -> {
            authService.loginAdmin(adminName, "wrongPassword");
        });
    }
    
    @Test
    void testLoginAdmin_FailWithNonExistingAdmin() {
        // given
        String password = "password123";
        
        // when & then
        assertThrows(UnauthorizedException.class, () -> {
            authService.loginAdmin("nonExistingAdmin", password);
        });
    }

    // 게스트 로그인 테스트
    @Test
    void testLoginGuest_SuccessWithNewGuest() {
        // given
        String name = "방문객";
        String phone = "010-1234-5678";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        String password = "guestPass123";
        
        // when
        Guest guest = authService.loginGuest(name, phone, birthDate, password);
        
        // then
        assertNotNull(guest);
        assertEquals(name, guest.getName());
        assertEquals(phone, guest.getPhone());
        assertEquals(birthDate, guest.getBirthDate());
    }
    
    @Test
    void testLoginGuest_SuccessWithExistingGuest() {
        // given
        String name = "방문객";
        String phone = "010-1234-5678";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        String password = "guestPass123";
        
        // 첫 로그인으로 게스트 등록
        Guest guest = authService.loginGuest(name, phone, birthDate, password);
        assertNotNull(guest);
        
        // when
        Guest sameGuest = authService.loginGuest(name, phone, birthDate, password);
        
        // then
        assertNotNull(sameGuest);
        assertEquals(guest.getCustomerId(), sameGuest.getCustomerId());
    }

    // 회원 로그인 테스트
    @Test
    void testLoginMember_Success() {
        // given
        String memberId = "testMember";
        String password = "memberPass123";
        String name = "회원";
        String phone = "010-9876-5432";
        LocalDate birthDate = LocalDate.of(1995, 5, 10);
        String profileImage = "profile.jpg";
        
        // 회원 등록
        Long id = authService.registerMember(memberId, password, name, phone, birthDate, profileImage);
        assertNotNull(id);
        
        // when
        Member member = authService.loginMember(memberId, password);
        
        // then
        assertNotNull(member);
        assertEquals(memberId, member.getMemberId());
        assertEquals(name, member.getName());
    }
    
    @Test
    void testLoginMember_FailWithWrongPassword() {
        // given
        String memberId = "testMember";
        String password = "memberPass123";
        String name = "회원";
        String phone = "010-9876-5432";
        LocalDate birthDate = LocalDate.of(1995, 5, 10);
        String profileImage = "profile.jpg";
        
        // 회원 등록
        Long id = authService.registerMember(memberId, password, name, phone, birthDate, profileImage);
        assertNotNull(id);
        
        // when & then
        assertThrows(UnauthorizedException.class, () -> {
            authService.loginMember(memberId, "wrongPassword");
        });
    }
    
    @Test
    void testLoginMember_FailWithNonExistingMember() {
        // given
        String password = "memberPass123";
        
        // when & then
        assertThrows(UnauthorizedException.class, () -> {
            authService.loginMember("nonExistingMember", password);
        });
    }

    // 관리자 등록 테스트
    @Test
    void testRegisterAdmin_Success() {
        // given
        String adminName = "newAdmin";
        String password = "adminPass123";
        
        // when
        Long adminId = authService.registerAdmin(adminName, password);
        
        // then
        assertNotNull(adminId);
        
        // 등록된 관리자로 로그인 가능해야 함
        Admin admin = authService.loginAdmin(adminName, password);
        assertNotNull(admin);
        assertEquals(adminName, admin.getName());
    }
    
    // 이미 존재하는 관리자 이름으로 등록 시도 시 실패 테스트
    @Test
    void testRegisterAdmin_FailWithExistingAdmin() {
        // given
        String adminName = "existingAdmin";
        String password = "adminPass123";
        
        // 첫 등록
        authService.registerAdmin(adminName, password);
        
        // 다시 등록 시도
        assertThrows(BadRequestException.class, () -> {
            authService.registerAdmin(adminName, password);
        });
    }

    // 회원 등록 테스트
    @Test
    void testRegisterMember_Success() {
        // given
        String memberId = "newMember";
        String password = "memberPass123";
        String name = "신규회원";
        String phone = "010-1111-2222";
        LocalDate birthDate = LocalDate.of(2000, 12, 31);
        String profileImage = "newProfile.jpg";
        
        // when
        Long id = authService.registerMember(memberId, password, name, phone, birthDate, profileImage);
        
        // then
        assertNotNull(id);
    }
    
    // 이미 존재하는 회원 ID로 등록 시도 시 실패 테스트
    @Test
    void testRegisterMember_FailWithExistingMemberId() {
        // given
        String memberId = "existingMember";
        String password = "memberPass123";
        String name = "기존회원";
        String phone = "010-1111-2222";
        LocalDate birthDate = LocalDate.of(2000, 12, 31);
        String profileImage = "existingProfile.jpg";
        
        // 첫 등록
        authService.registerMember(memberId, password, name, phone, birthDate, profileImage);
        
        // 다시 등록 시도
        assertThrows(BadRequestException.class, () -> {
            authService.registerMember(memberId, password, name, phone, birthDate, profileImage);
        });
    }
    
}
