package com.uos.dsd.cinema.adaptor.in.web;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uos.dsd.cinema.common.response.ApiResponse;

@RestController
@Profile("test")
public class TestPageController {
    
    @GetMapping("/member/test-page")
    public ResponseEntity<ApiResponse<String>> getMemberPage() {
        return ResponseEntity.ok(ApiResponse.success("회원 페이지에 접근했습니다."));
    }
    
    @GetMapping("/admin/test-page")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> getAdminPage() {
        return ResponseEntity.ok(ApiResponse.success("관리자 페이지에 접근했습니다."));
    }
    
    @GetMapping("/guest/test-page")
    public ResponseEntity<ApiResponse<String>> getGuestPage() {
        return ResponseEntity.ok(ApiResponse.success("게스트 페이지에 접근했습니다."));
    }
} 
