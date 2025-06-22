package com.uos.dsd.cinema.application.port.in.customer.member.command;

public record DeleteMemberCommand(
    String username,
    String password
) { } 
