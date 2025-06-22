package com.uos.dsd.cinema.adaptor.in.web.customer.member.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;

public record MemberUpdateRequest(
    @NotBlank(message = "Current password is required")
    String password,

    String newPassword,

    String name,
    
    String phone,
    
    String birthDate,
    
    MultipartFile profileImage
) { } 
