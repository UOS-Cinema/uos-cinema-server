package com.uos.dsd.cinema.domain.model.user;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "guests")
@Setter
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Guest {
    @Id
    @Column(name = "customer_id")
    private Long customerId;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private LocalDate birthDate;
    
    @Column(nullable = false)
    private String password;

    @Builder
    public Guest(String name, String phone, LocalDate birthDate, String password) {
        this.name = name;
        this.phone = phone;
        this.birthDate = birthDate;
        this.password = password;
    }
} 
