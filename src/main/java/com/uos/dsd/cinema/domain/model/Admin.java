package com.uos.dsd.cinema.domain.model;

import com.uos.dsd.cinema.common.model.Base;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;

    public Admin(String name, String encodedPassword) {
        this.name = name;
        this.password = encodedPassword;
    }

    public boolean isPasswordMatched(String encodedPassword) {
        return this.password.equals(encodedPassword);
    }
}
