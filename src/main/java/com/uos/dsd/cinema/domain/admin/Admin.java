package com.uos.dsd.cinema.domain.admin;

import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.exception.http.BadRequestException;
import com.uos.dsd.cinema.common.model.Base;
import com.uos.dsd.cinema.common.utils.PasswordUtil;
import com.uos.dsd.cinema.domain.constraint.PasswordConstraint;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String name;
    private String password;

    public Admin(String name, String password) {

        this.name = name;
        setPassword(password);
    }

    public boolean isPasswordMatched(String password) {

        byte[] salt = PasswordUtil.extractSalt(this.password);
        String hashedPassword = PasswordUtil.hashPasswordWithSalt(password, salt);
        return this.password.equals(hashedPassword);
    }

    private void setPassword(String password) {

        if (!PasswordConstraint.isValidPassword(password)) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "Invalid password format");
        }
        this.password = PasswordUtil.hashPasswordWithSalt(password, PasswordUtil.generateSalt());
    }
}
