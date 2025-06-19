package com.uos.dsd.cinema.domain.movie;

import com.uos.dsd.cinema.common.model.Base;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "movie_casts")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieCast extends Base {

    @EmbeddedId
    private MovieCastId id;

    @Column(nullable = true)
    private String role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CastingType castingType;

    public MovieCast(Movie movie, Long actorId, String role, CastingType castingType) {
        this.id = new MovieCastId(movie, actorId);
        this.role = role;
        this.castingType = castingType;
    }
} 
