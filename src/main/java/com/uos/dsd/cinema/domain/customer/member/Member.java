package com.uos.dsd.cinema.domain.customer.member;

import com.uos.dsd.cinema.common.utils.PasswordUtil;
import com.uos.dsd.cinema.domain.common.constraint.PasswordConstraint;
import com.uos.dsd.cinema.domain.common.constraint.UsernameConstraint;
import com.uos.dsd.cinema.domain.customer.Customer;
import com.uos.dsd.cinema.domain.customer.UserType;
import com.uos.dsd.cinema.domain.customer.common.constraint.BirthDateConstraint;
import com.uos.dsd.cinema.domain.customer.common.constraint.NameConstraint;
import com.uos.dsd.cinema.domain.customer.common.constraint.PhoneConstraint;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "members")
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    @Column(name = "customer_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    @MapsId
    private Customer customer;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Member(String username, String password, String name, String phone, LocalDate birthDate,
        String profileImageUrl) {
        this.customer = new Customer(UserType.MEMBER);
        validateAndSetUsername(username);
        validateAndSetPassword(password);
        validateAndSetName(name);
        validateAndSetPhone(phone);
        validateAndSetBirthDate(birthDate);
        this.profileImageUrl = profileImageUrl;
    }

    private void validateAndSetUsername(String username) {
        UsernameConstraint.validateUsername(username);
        this.username = username;
    }

    private void validateAndSetPassword(String password) {
        PasswordConstraint.validatePassword(password);
        this.password = PasswordUtil.hashPasswordWithSalt(password, PasswordUtil.generateSalt());
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
        if (birthDate != null) {
            BirthDateConstraint.validateBirthDate(birthDate);
        }
        this.birthDate = birthDate;
    }

    public boolean isPasswordMatched(String password) {
        byte[] salt = PasswordUtil.extractSalt(this.password);
        String hashedPassword = PasswordUtil.hashPasswordWithSalt(password, salt);
        return this.password.equals(hashedPassword);
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }
}
