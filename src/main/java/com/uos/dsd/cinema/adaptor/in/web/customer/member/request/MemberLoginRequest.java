package com.uos.dsd.cinema.adaptor.in.web.customer.member.request;

import jakarta.validation.constraints.NotBlank;

public record MemberLoginRequest(
    @NotBlank(message = "Username is required")
    String username,
    
    @NotBlank(message = "Password is required")
    String password
) { } 
