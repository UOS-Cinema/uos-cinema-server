package com.uos.dsd.cinema.domain.movie;

import com.uos.dsd.cinema.common.model.Base;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "movie_genres")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieGenre extends Base {

    @EmbeddedId
    private MovieGenreId id;

    public MovieGenre(Movie movie, String genreName) {
        this.id = new MovieGenreId(movie, genreName);
    }
} 
