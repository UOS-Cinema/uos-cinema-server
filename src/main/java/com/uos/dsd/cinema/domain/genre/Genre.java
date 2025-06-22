package com.uos.dsd.cinema.domain.genre;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "genres")
@Getter
@ToString
@EqualsAndHashCode(of = "name")
@NoArgsConstructor
public class Genre {

    @Id
    private String name;

    private String description;

    private String imageUrl;

    public Genre(String name, String description, String imageUrl) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Genre name cannot be null or blank");
        }
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public static Genre reference(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Genre name cannot be null or blank");
        }
        return new Genre(name);
    }

    private Genre(String name) {
        this.name = name;
    }

    public void modifyDescription(String description) {
        this.description = description;
    }

    public void modifyImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
