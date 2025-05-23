package com.uos.dsd.cinema.application.port.in.admin.command;

public record UpdateAdminCommand(Long id, String currentPassword, String newPassword) {
}
