package com.uos.dsd.cinema.adaptor.in.web.request;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.uos.dsd.cinema.application.service.AuthService;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.core.security.JwtUtils;
import com.uos.dsd.cinema.core.security.SecurityConstants.Role;
import com.uos.dsd.cinema.domain.model.user.Admin;
import com.uos.dsd.cinema.domain.model.user.Guest;
import com.uos.dsd.cinema.domain.model.user.Member;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    private final JwtUtils jwtUtils;
    private final AuthRequestValidator validator;
    
    private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";

    @Value("${jwt.refreshTokenExpirationMs}")
    private long refreshTokenExpirationMs;
    
    @PostMapping("/login/member")
    public ResponseEntity<ApiResponse<AccessTokenResponse>> loginMember(
            @RequestBody MemberLoginRequest request, 
            HttpServletResponse response) {
        validator.validateMemberLoginRequest(request);
        Member member = authService.loginMember(request.memberId(), request.password());
        
        String accessToken = jwtUtils.generateAccessToken(member.getMemberId(), Role.MEMBER.name());
        String refreshToken = jwtUtils.generateRefreshToken(member.getMemberId());
        
        addRefreshTokenCookie(response, refreshToken);
        
        return ResponseEntity.ok(ApiResponse.success(new AccessTokenResponse(accessToken)));
    }
    
    @PostMapping("/login/guest")
    public ResponseEntity<ApiResponse<AccessTokenResponse>> loginGuest(
            @RequestBody GuestLoginRequest request, 
            HttpServletResponse response) {
        validator.validateGuestLoginRequest(request);
        Guest guest = authService.loginGuest(
            request.name(), 
            request.phone(), 
            request.birthDate(), 
            request.password()
        );
        
        String accessToken = jwtUtils.generateAccessToken(guest.getPhone(), Role.GUEST.name());
        String refreshToken = jwtUtils.generateRefreshToken(guest.getPhone());
        
        addRefreshTokenCookie(response, refreshToken);
        
        return ResponseEntity.ok(ApiResponse.success(new AccessTokenResponse(accessToken)));
    }
    
    @PostMapping("/login/admin")
    public ResponseEntity<ApiResponse<AccessTokenResponse>> loginAdmin(
            @RequestBody AdminLoginRequest request, 
            HttpServletResponse response) {
        validator.validateAdminLoginRequest(request);
        Admin admin = authService.loginAdmin(request.name(), request.password());
        
        String accessToken = jwtUtils.generateAccessToken(admin.getName(), Role.ADMIN.name());
        String refreshToken = jwtUtils.generateRefreshToken(admin.getName());
        
        addRefreshTokenCookie(response, refreshToken);
        
        return ResponseEntity.ok(ApiResponse.success(new AccessTokenResponse(accessToken)));
    }
    
    @PostMapping("/signup/member")
    public ResponseEntity<ApiResponse<Void>> registerMember(@RequestBody MemberSignupRequest request) {
        validator.validateMemberSignupRequest(request);
        authService.registerMember(
            request.memberId(),
            request.password(),
            request.name(),
            request.phone(),
            request.birthDate(),
            request.profileImage()
        );
        
        return ResponseEntity.ok(ApiResponse.success());
    }
    
    @PostMapping("/signup/admin")
    public ResponseEntity<ApiResponse<Void>> registerAdmin(@RequestBody AdminSignupRequest request) {
        validator.validateAdminSignupRequest(request);
        authService.registerAdmin(request.name(), request.password());
        
        return ResponseEntity.ok(ApiResponse.success());
    }
    
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AccessTokenResponse>> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        
        
        String refreshToken = extractRefreshTokenFromCookies(request);
        String accessToken = extractAccessTokenFromHeader(request);  // 만료되었을 수 있음
        
        if (accessToken != null && refreshToken != null && jwtUtils.validateJwtToken(refreshToken)) {
            String username = jwtUtils.getUsernameFromJwtToken(refreshToken);
            String role = jwtUtils.getRoleFromJwtToken(accessToken);
            
            String newAccessToken = jwtUtils.generateAccessToken(username, role);
            String newRefreshToken = jwtUtils.generateRefreshToken(username);
            
            addRefreshTokenCookie(response, newRefreshToken);
            
            return ResponseEntity.ok(ApiResponse.success(new AccessTokenResponse(newAccessToken)));
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.failure(CommonResultCode.UNAUTHORIZED));
    }
    
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletResponse response) {
        // 리프레시 토큰 쿠키 삭제
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, "");
        cookie.setMaxAge(0); // 즉시 만료
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // HTTPS에서만 전송
        response.addCookie(cookie);
        
        return ResponseEntity.ok(ApiResponse.success());
    }
    
    // 쿠키에 리프레시 토큰 추가
    private void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, refreshToken)
                .maxAge(refreshTokenExpirationMs)
                .httpOnly(true)
                .secure(true) // HTTPS에서만 전송
                .path("/")
                .sameSite("Strict")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
    
    // 쿠키에서 리프레시 토큰 추출
    private String extractRefreshTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (REFRESH_TOKEN_COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    
    // Authorization 헤더에서 액세스 토큰 추출
    private String extractAccessTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    
    // 요청 및 응답 DTO
    public record MemberLoginRequest(String memberId, String password) {}
    
    public record GuestLoginRequest(String name, String phone, LocalDate birthDate, String password) {}
    
    public record AdminLoginRequest(String name, String password) {}
    
    public record MemberSignupRequest(
        String memberId,
        String password,
        String name,
        String phone,
        LocalDate birthDate,
        String profileImage
    ) {}
    
    public record AdminSignupRequest(String name, String password) {}
    
    public record AccessTokenResponse(String accessToken) {}
}
