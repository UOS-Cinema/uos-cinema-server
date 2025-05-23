package com.uos.dsd.cinema.application.port.in.admin.command;

public record UpdateAdminCommand(Long adminId, String currentPassword, String newPassword) {
}
