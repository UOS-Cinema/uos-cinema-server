package com.uos.dsd.cinema.domain.customer.guest.command;

import com.uos.dsd.cinema.common.utils.PasswordUtil;
import com.uos.dsd.cinema.domain.common.constraint.PasswordConstraint;
import com.uos.dsd.cinema.domain.customer.Customer;
import com.uos.dsd.cinema.domain.customer.UserType;
import com.uos.dsd.cinema.domain.customer.constraint.BirthDateConstraint;
import com.uos.dsd.cinema.domain.customer.constraint.NameConstraint;
import com.uos.dsd.cinema.domain.customer.constraint.PhoneConstraint;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "guests")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Guest {

    @Id
    @Column(name = "customer_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    @MapsId
    private Customer customer;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String password;

    public Guest(String name, String phone, LocalDate birthDate, String password) {

        this.customer = new Customer(UserType.GUEST);
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
