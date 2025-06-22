package com.uos.dsd.cinema.application.port.in.customer.member.command;

public record LoginMemberCommand(
    String username,
    String password
) { } 
