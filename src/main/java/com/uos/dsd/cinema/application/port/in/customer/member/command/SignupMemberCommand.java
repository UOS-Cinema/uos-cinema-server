package com.uos.dsd.cinema.application.port.in.customer.member.command;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record SignupMemberCommand(
    String username,
    String password,
    String name,
    String phone,
    LocalDate birthDate,
    MultipartFile profileImage
) { } 
