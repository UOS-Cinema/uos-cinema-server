package com.uos.dsd.cinema.common.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class Base {

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime createdAt;
    @LastModifiedDate
    protected LocalDateTime updatedAt;
    protected LocalDateTime deletedAt;

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isDeleted() {
        return deletedAt != null;
    }
}
