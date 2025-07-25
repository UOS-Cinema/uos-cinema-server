package com.uos.dsd.cinema.domain.admin;

import com.uos.dsd.cinema.common.model.Base;
import com.uos.dsd.cinema.common.utils.PasswordUtil;
import com.uos.dsd.cinema.domain.common.constraint.PasswordConstraint;
import com.uos.dsd.cinema.domain.common.constraint.UsernameConstraint;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "admins")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    public Admin(String username, String password) {

        validateAndSetUsername(username);
        validateAndSetPassword(password);
    }

    public void updatePassword(String newPassword) {
        validateAndSetPassword(newPassword);
    }

    public boolean isPasswordMatched(String password) {

        byte[] salt = PasswordUtil.extractSalt(this.password);
        String hashedPassword = PasswordUtil.hashPasswordWithSalt(password, salt);
        return this.password.equals(hashedPassword);
    }

    private void validateAndSetUsername(String username) {

        UsernameConstraint.validateUsername(username);
        this.username = username;
    }

    private void validateAndSetPassword(String password) {

        PasswordConstraint.validatePassword(password);
        this.password = PasswordUtil.hashPasswordWithSalt(password, PasswordUtil.generateSalt());
    }
}
