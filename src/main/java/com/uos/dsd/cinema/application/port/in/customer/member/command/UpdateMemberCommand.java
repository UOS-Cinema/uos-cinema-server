package com.uos.dsd.cinema.application.port.in.customer.member.command;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record UpdateMemberCommand(
    String username,
    String password,
    String newPassword,
    String name,
    String phone,
    LocalDate birthDate,
    MultipartFile profileImage
) { } 
