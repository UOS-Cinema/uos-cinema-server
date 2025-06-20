package com.uos.dsd.cinema.application.port.in.admin.command;

public record AdminUpdateCommand(Long id, String currentPassword, String newPassword) {
}
