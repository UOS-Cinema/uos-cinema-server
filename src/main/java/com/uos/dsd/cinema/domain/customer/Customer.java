package com.uos.dsd.cinema.domain.customer;

import org.springframework.data.annotation.CreatedDate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime createdAt;
    
    public Customer(UserType userType) {

        if (userType == null) {
            throw new IllegalArgumentException("User type is required");
        }
        this.userType = userType;
    }
}
