package com.uos.dsd.cinema.domain.model.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "members")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Member {
    @Id
    @Column(name = "customer_id")
    private Long customerId;
    
    @Column(unique = true, nullable = false)
    private String memberId;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String name;
    
    @Column(unique = true, nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private LocalDate birthDate;
    
    private String profileImage;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    private LocalDateTime deletedAt;

    @Builder
    public Member(String memberId, String password, String name, String phone, LocalDate birthDate, String profileImage) {
        this.memberId = memberId;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.birthDate = birthDate;
        this.profileImage = profileImage;
    }
} 
