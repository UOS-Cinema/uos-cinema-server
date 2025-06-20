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
@Table(name = "movie_screen_types")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieScreenType extends Base {

    @EmbeddedId
    private MovieScreenTypeId id;

    public MovieScreenType(Movie movie, String screenType) {
        this.id = new MovieScreenTypeId(movie, screenType);
    }
}
