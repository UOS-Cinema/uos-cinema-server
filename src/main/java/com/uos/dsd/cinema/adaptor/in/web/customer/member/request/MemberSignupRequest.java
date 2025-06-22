package com.uos.dsd.cinema.adaptor.in.web.customer.member.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MemberSignupRequest(
    
    @NotBlank(message = "Username is required")
    String username,
    
    @NotBlank(message = "Password is required")
    String password,
    
    @NotBlank(message = "Name is required")
    String name,
    
    @NotBlank(message = "Phone is required")
    String phone,
    
    @NotNull(message = "Birth date is required")
    String birthDate,
    
    MultipartFile profileImage
) { } 
