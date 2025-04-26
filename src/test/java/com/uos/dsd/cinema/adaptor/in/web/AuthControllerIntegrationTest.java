package com.uos.dsd.cinema.adaptor.in.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.uos.dsd.cinema.adaptor.in.web.request.AuthController.AdminLoginRequest;
import com.uos.dsd.cinema.adaptor.in.web.request.AuthController.AdminSignupRequest;
import com.uos.dsd.cinema.adaptor.in.web.request.AuthController.GuestLoginRequest;
import com.uos.dsd.cinema.adaptor.in.web.request.AuthController.MemberLoginRequest;
import com.uos.dsd.cinema.adaptor.in.web.request.AuthController.MemberSignupRequest;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;

import jakarta.servlet.http.Cookie;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }
    
    @Nested
    @DisplayName("회원 가입 및 로그인 테스트")
    class MemberAuthenticationTests {
        
        @Test
        @DisplayName("회원 가입 성공 테스트")
        void testMemberSignupSuccess() throws Exception {
            // 회원 가입 요청
            MemberSignupRequest signupRequest = new MemberSignupRequest(
                    "testuser",
                    "password123",
                    "테스트사용자",
                    "010-1234-5678",
                    LocalDate.of(1990, 1, 1),
                    "profile.jpg");

            // 회원 가입 API 호출
            mockMvc.perform(post("/signup/member")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signupRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(CommonResultCode.SUCCESS.getCode()));
        }
        
        @Test
        @DisplayName("올바른 회원 로그인 성공 테스트")
        void testMemberLoginSuccess() throws Exception {
            // 회원 가입
            MemberSignupRequest signupRequest = new MemberSignupRequest(
                    "testuser",
                    "password123",
                    "테스트사용자",
                    "010-1234-5678",
                    LocalDate.of(1990, 1, 1),
                    "profile.jpg");
                    
            mockMvc.perform(post("/signup/member")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signupRequest)))
                    .andExpect(status().isOk());

            // 로그인 요청
            MemberLoginRequest loginRequest = new MemberLoginRequest("testuser", "password123");

            // 로그인 API 호출
            mockMvc.perform(post("/login/member")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(CommonResultCode.SUCCESS.getCode()))
                    .andExpect(jsonPath("$.data.accessToken").exists())
                    .andExpect(cookie().exists("refreshToken"));
        }
        
        @Test
        @DisplayName("잘못된 비밀번호로 회원 로그인 실패 테스트")
        void testMemberLoginFailWithWrongPassword() throws Exception {
            // 회원 가입
            MemberSignupRequest signupRequest = new MemberSignupRequest(
                    "testuser",
                    "password123",
                    "테스트사용자",
                    "010-1234-5678",
                    LocalDate.of(1990, 1, 1),
                    "profile.jpg");
                    
            mockMvc.perform(post("/signup/member")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signupRequest)))
                    .andExpect(status().isOk());

            // 잘못된 비밀번호 로그인
            MemberLoginRequest wrongPasswordRequest = new MemberLoginRequest("testuser", "wrongpassword");

            mockMvc.perform(post("/login/member")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(wrongPasswordRequest)))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("존재하지 않는 회원 로그인 실패 테스트")
        void testMemberLoginFailWithNonExistingUser() throws Exception {
            // 존재하지 않는 회원 로그인
            MemberLoginRequest nonExistingUserRequest = new MemberLoginRequest("nonexistinguser", "password123");

            mockMvc.perform(post("/login/member")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(nonExistingUserRequest)))
                    .andExpect(status().isUnauthorized());
        }
    }
    
    @Nested
    @DisplayName("관리자 가입 및 로그인 테스트")
    class AdminAuthenticationTests {
        
        @Test
        @DisplayName("관리자 가입 성공 테스트")
        void testAdminSignupSuccess() throws Exception {
            // 관리자 가입 요청
            AdminSignupRequest adminSignupRequest = new AdminSignupRequest("admin", "adminpass123");

            // 관리자 가입 API 호출
            mockMvc.perform(post("/signup/admin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(adminSignupRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(CommonResultCode.SUCCESS.getCode()));
        }
        
        @Test
        @DisplayName("올바른 관리자 로그인 성공 테스트")
        void testAdminLoginSuccess() throws Exception {
            // 관리자 가입
            AdminSignupRequest adminSignupRequest = new AdminSignupRequest("admin", "adminpass123");
            
            mockMvc.perform(post("/signup/admin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(adminSignupRequest)))
                    .andExpect(status().isOk());

            // 관리자 로그인 요청
            AdminLoginRequest adminLoginRequest = new AdminLoginRequest("admin", "adminpass123");

            // 관리자 로그인 API 호출
            mockMvc.perform(post("/login/admin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(adminLoginRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(CommonResultCode.SUCCESS.getCode()))
                    .andExpect(jsonPath("$.data.accessToken").exists())
                    .andExpect(cookie().exists("refreshToken"));
        }
        
        @Test
        @DisplayName("잘못된 비밀번호로 관리자 로그인 실패 테스트")
        void testAdminLoginFailWithWrongPassword() throws Exception {
            // 관리자 가입
            AdminSignupRequest adminSignupRequest = new AdminSignupRequest("admin", "adminpass123");
            
            mockMvc.perform(post("/signup/admin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(adminSignupRequest)))
                    .andExpect(status().isOk());

            // 잘못된 비밀번호 로그인
            AdminLoginRequest wrongPasswordRequest = new AdminLoginRequest("admin", "wrongadminpass");

            mockMvc.perform(post("/login/admin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(wrongPasswordRequest)))
                    .andExpect(status().isUnauthorized());
        }
    }
    
    @Nested
    @DisplayName("게스트 로그인 테스트")
    class GuestAuthenticationTests {
        
        @Test
        @DisplayName("게스트 로그인 성공 테스트")
        void testGuestLoginSuccess() throws Exception {
            // 게스트 로그인 요청
            GuestLoginRequest guestLoginRequest = new GuestLoginRequest(
                    "방문객",
                    "010-9876-5432",
                    LocalDate.of(1995, 5, 5),
                    "guestpass");

            // 게스트 로그인 API 호출
            mockMvc.perform(post("/login/guest")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(guestLoginRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(CommonResultCode.SUCCESS.getCode()))
                    .andExpect(jsonPath("$.data.accessToken").exists())
                    .andExpect(cookie().exists("refreshToken"));
        }

        @Test
        @DisplayName("필수 정보 누락 시 게스트 로그인 실패 테스트")
        void testGuestLoginFailWithMissingInfo() throws Exception {
            // 필수 정보 누락된 게스트 로그인 요청 (연락처 없음)
            GuestLoginRequest invalidGuestRequest = new GuestLoginRequest(
                    "방문객",
                    "",  // 연락처 누락
                    LocalDate.of(1995, 5, 5),
                    "guestpass");

            // 게스트 로그인 API 호출
            mockMvc.perform(post("/login/guest")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidGuestRequest)))
                    .andExpect(status().isBadRequest());
        }
    }
    
    @Nested
    @DisplayName("토큰 갱신 및 로그아웃 테스트")
    class TokenManagementTests {
        
        @Test
        @DisplayName("리프레시 토큰을 통한 액세스 토큰 갱신 테스트")
        void testRefreshToken() throws Exception {
            // 회원 가입
            MemberSignupRequest signupRequest = new MemberSignupRequest(
                    "refreshuser",
                    "password123",
                    "리프레시유저",
                    "010-1111-2222",
                    LocalDate.of(1990, 1, 1),
                    "profile.jpg");

            mockMvc.perform(post("/signup/member")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signupRequest)))
                    .andExpect(status().isOk());

            // 로그인 요청
            MemberLoginRequest loginRequest = new MemberLoginRequest("refreshuser", "password123");

            // 로그인 API 호출하여 토큰 획득
            MvcResult result = mockMvc.perform(post("/login/member")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginRequest)))
                    .andExpect(status().isOk())
                    .andReturn();

            // 쿠키에서 리프레시 토큰 추출
            String refreshTokenCookie = result.getResponse().getCookie("refreshToken").getValue();

            // JSON 응답에서 액세스 토큰 추출
            String responseJson = result.getResponse().getContentAsString();
            String accessToken = objectMapper.readTree(responseJson).get("data").get("accessToken").asText();

            // 리프레시 토큰 API 호출
            mockMvc.perform(post("/refresh-token")
                    .cookie(new Cookie("refreshToken", refreshTokenCookie))
                    .header("Authorization", "Bearer " + accessToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(CommonResultCode.SUCCESS.getCode()))
                    .andExpect(jsonPath("$.data.accessToken").exists())
                    .andExpect(cookie().exists("refreshToken"));
        }
        
        @Test
        @DisplayName("유효하지 않은 리프레시 토큰으로 갱신 실패 테스트")
        void testRefreshTokenFailWithInvalidToken() throws Exception {
            // 유효하지 않은 리프레시 토큰
            String invalidRefreshToken = "invalid_refresh_token";

            // 리프레시 토큰 API 호출
            mockMvc.perform(post("/refresh-token")
                    .cookie(new Cookie("refreshToken", invalidRefreshToken))
                    .header("Authorization", "Bearer invalid_access_token"))
                    .andExpect(status().isUnauthorized());
        }
        
        @Test
        @DisplayName("로그아웃 테스트")
        void testLogout() throws Exception {
            // 회원 가입
            MemberSignupRequest signupRequest = new MemberSignupRequest(
                    "logoutuser",
                    "password123",
                    "로그아웃유저",
                    "010-3333-4444",
                    LocalDate.of(1990, 1, 1),
                    "profile.jpg");

            mockMvc.perform(post("/signup/member")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signupRequest)))
                    .andExpect(status().isOk());

            // 로그인 요청
            MemberLoginRequest loginRequest = new MemberLoginRequest("logoutuser", "password123");

            // 로그인 API 호출하여 리프레시 토큰 쿠키 획득
            mockMvc.perform(post("/login/member")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginRequest)))
                    .andExpect(status().isOk())
                    .andExpect(cookie().exists("refreshToken"));

            // 로그아웃 API 호출 - 커스텀 설정으로 200 응답 및 쿠키 삭제 확인
            mockMvc.perform(post("/logout"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(CommonResultCode.SUCCESS.getCode()))
                    .andExpect(cookie().maxAge("refreshToken", 0));
        }
    }
    
    @Nested
    @DisplayName("권한별 페이지 접근 테스트")
    class PageAccessTests {
        
        @Test
        @DisplayName("회원 권한으로 페이지 접근 테스트")
        void testMemberPageAccess() throws Exception {
            // 회원 가입
            MemberSignupRequest signupRequest = new MemberSignupRequest(
                    "pageaccessuser",
                    "password123",
                    "페이지접근유저",
                    "010-5555-6666",
                    LocalDate.of(1990, 1, 1),
                    "profile.jpg");

            mockMvc.perform(post("/signup/member")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signupRequest)))
                    .andExpect(status().isOk());

            // 회원 로그인
            MemberLoginRequest loginRequest = new MemberLoginRequest("pageaccessuser", "password123");
            MvcResult memberResult = mockMvc.perform(post("/login/member")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginRequest)))
                    .andExpect(status().isOk())
                    .andReturn();

            // 액세스 토큰 추출
            String memberToken = objectMapper.readTree(memberResult.getResponse().getContentAsString())
                    .get("data").get("accessToken").asText();

            // 회원은 회원 페이지에 접근 가능
            mockMvc.perform(get("/member/test-page")
                    .header("Authorization", "Bearer " + memberToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(CommonResultCode.SUCCESS.getCode()));
                    
            // 회원은 관리자 페이지에 접근 불가능
            mockMvc.perform(get("/admin/test-page")
                    .header("Authorization", "Bearer " + memberToken))
                    .andExpect(status().isForbidden())
                    .andExpect(jsonPath("$.code").value(CommonResultCode.FORBIDDEN.getCode()));
        }
        
        @Test
        @DisplayName("게스트 권한으로 페이지 접근 테스트")
        void testGuestPageAccess() throws Exception {
            // 게스트 로그인
            GuestLoginRequest guestLoginRequest = new GuestLoginRequest(
                    "게스트접근",
                    "010-7777-8888",
                    LocalDate.of(1995, 5, 5),
                    "guestpass");
            MvcResult guestResult = mockMvc.perform(post("/login/guest")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(guestLoginRequest)))
                    .andExpect(status().isOk())
                    .andReturn();

            // 액세스 토큰 추출
            String guestToken = objectMapper.readTree(guestResult.getResponse().getContentAsString())
                    .get("data").get("accessToken").asText();
                    
            // 게스트도 회원 페이지에 접근 가능
            mockMvc.perform(get("/member/test-page")
                    .header("Authorization", "Bearer " + guestToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(CommonResultCode.SUCCESS.getCode()));
                    
            // 게스트는 관리자 페이지에 접근 불가능
            mockMvc.perform(get("/admin/test-page")
                    .header("Authorization", "Bearer " + guestToken))
                    .andExpect(status().isForbidden())
                    .andExpect(jsonPath("$.code").value(CommonResultCode.FORBIDDEN.getCode()));
        }
        
        @Test
        @DisplayName("관리자 권한으로 페이지 접근 테스트")
        void testAdminPageAccess() throws Exception {
            // 관리자 가입
            AdminSignupRequest adminSignupRequest = new AdminSignupRequest("adminaccess", "adminpass123");
            mockMvc.perform(post("/signup/admin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(adminSignupRequest)))
                    .andExpect(status().isOk());

            // 관리자 로그인
            AdminLoginRequest adminLoginRequest = new AdminLoginRequest("adminaccess", "adminpass123");
            MvcResult adminResult = mockMvc.perform(post("/login/admin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(adminLoginRequest)))
                    .andExpect(status().isOk())
                    .andReturn();

            // 액세스 토큰 추출
            String adminToken = objectMapper.readTree(adminResult.getResponse().getContentAsString())
                    .get("data").get("accessToken").asText();
                    
            // 관리자도 회원 페이지에 접근 가능
            mockMvc.perform(get("/member/test-page")
                    .header("Authorization", "Bearer " + adminToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(CommonResultCode.SUCCESS.getCode()));
                    
            // 관리자는 관리자 페이지에 접근 가능
            mockMvc.perform(get("/admin/test-page")
                    .header("Authorization", "Bearer " + adminToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(CommonResultCode.SUCCESS.getCode()));
        }
        
        @Test
        @DisplayName("인증되지 않은 사용자의 페이지 접근 테스트")
        void testUnauthenticatedPageAccess() throws Exception {
            // 인증 없이 회원 페이지 접근 시도
            mockMvc.perform(get("/member/test-page"))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.code").value(CommonResultCode.UNAUTHORIZED.getCode()));
                    
            // 인증 없이 관리자 페이지 접근 시도
            mockMvc.perform(get("/admin/test-page"))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.code").value(CommonResultCode.UNAUTHORIZED.getCode()));
                    
            // 인증 없이 게스트 페이지 접근 시도
            mockMvc.perform(get("/guest/test-page"))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.code").value(CommonResultCode.UNAUTHORIZED.getCode()));
        }
    }
}
