package com.uos.dsd.cinema.adaptor.in.web.hello;

import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.core.annotation.UserId;
import com.uos.dsd.cinema.core.annotation.UserRole;
import com.uos.dsd.cinema.core.security.SecurityConstants.Role;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    public ApiResponse<String> hello() {
        return ApiResponse.success("Hello, UOS Cinema!");
    }

    @GetMapping("/auth")
    public ApiResponse<String> helloAuth(@UserId Long userId, @UserRole Role role) {
        return ApiResponse.success("Hello, UOS Cinema! " + userId + " " + role.name());
    }
}
