package com.uos.dsd.cinema.domain.guest;

import java.time.LocalDate;

import com.uos.dsd.cinema.common.utils.PasswordUtil;
import com.uos.dsd.cinema.domain.constraint.BirthDateConstraint;
import com.uos.dsd.cinema.domain.constraint.NameConstraint;
import com.uos.dsd.cinema.domain.constraint.PasswordConstraint;
import com.uos.dsd.cinema.domain.constraint.PhoneConstraint;
import com.uos.dsd.cinema.domain.customer.Customer;
import com.uos.dsd.cinema.domain.customer.UserType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "guests")
@PrimaryKeyJoinColumn(name = "customer_id")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Guest extends Customer {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String password;

    public Guest(String name, String phone, LocalDate birthDate, String password) {

        super(UserType.GUEST);
        validateAndSetName(name);
        validateAndSetPhone(phone);
        validateAndSetBirthDate(birthDate);
        validateAndSetPassword(password);
    }

    private void validateAndSetName(String name) {

        NameConstraint.validateName(name);
        this.name = name;
    }

    private void validateAndSetPhone(String phone) {

        PhoneConstraint.validatePhone(phone);
        this.phone = phone.replaceAll("-", "");
    }

    private void validateAndSetBirthDate(LocalDate birthDate) {

        BirthDateConstraint.validateBirthDate(birthDate);
        this.birthDate = birthDate;
    }

    private void validateAndSetPassword(String password) {

        PasswordConstraint.validatePassword(password);
        this.password = PasswordUtil.hashPasswordWithSalt(password, PasswordUtil.generateSalt());
    }

    public boolean isPasswordMatched(String password) {

        byte[] salt = PasswordUtil.extractSalt(this.password);
        String hashedPassword = PasswordUtil.hashPasswordWithSalt(password, salt);
        return this.password.equals(hashedPassword);
    }
}
