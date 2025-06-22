package com.uos.dsd.cinema.domain.movie;

import com.uos.dsd.cinema.domain.actor.Actor;
import com.uos.dsd.cinema.domain.movie.enums.CastingType;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "movie_casts")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieCast {

    @EmbeddedId
    private MovieCastId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("movieId")
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id", insertable = false, updatable = false)
    private Actor actor;

    @Column(nullable = true)
    private String role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CastingType castingType;

    public MovieCast(Movie movie, Casting casting) {
        this.id = new MovieCastId(movie.getId(), casting.actorId());
        this.movie = movie;
        this.role = casting.role();
        this.castingType = casting.castingType();
    }
} 
