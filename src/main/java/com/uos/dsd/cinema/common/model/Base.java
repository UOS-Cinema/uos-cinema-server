package com.uos.dsd.cinema.common.model;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public abstract class Base {

    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected LocalDateTime deletedAt;

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}
