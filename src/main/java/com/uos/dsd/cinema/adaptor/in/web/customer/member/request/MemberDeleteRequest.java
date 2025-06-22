package com.uos.dsd.cinema.adaptor.in.web.customer.member.request;

import jakarta.validation.constraints.NotBlank;

public record MemberDeleteRequest(
    @NotBlank(message = "Password is required")
    String password
) {
} 
