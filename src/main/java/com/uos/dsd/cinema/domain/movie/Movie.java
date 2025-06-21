package com.uos.dsd.cinema.domain.movie;

import com.uos.dsd.cinema.common.converter.StringListConverter;
import com.uos.dsd.cinema.common.model.Base;
import com.uos.dsd.cinema.domain.actor.Actor;
import com.uos.dsd.cinema.domain.director.Director;
import com.uos.dsd.cinema.domain.genre.Genre;
import com.uos.dsd.cinema.domain.movie.enums.CastingType;
import com.uos.dsd.cinema.domain.movie.enums.MovieRating;
import com.uos.dsd.cinema.domain.screen_type.ScreenType;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = true)
    private String synopsis;

    @Column(nullable = false)
    private Integer runningTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovieRating rating;

    @Lob
    @Convert(converter = StringListConverter.class)
    @Column(name = "poster_urls")
    private List<String> posterUrls;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(nullable = false)
    private String distributorName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "director_id")
    private Director director;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "movie_screen_types",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "screen_type"))
    private List<ScreenType> screenTypes;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieCast> movieCasts;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "movie_genres",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_name"))
    private List<Genre> genres;

    public Movie(String title,
            String synopsis,
            Integer runningTime,
            MovieRating rating,
            List<String> posterUrls,
            LocalDate releaseDate,
            String distributorName,
            Director director,
            List<ScreenType> screenTypes,
            List<Actor> actors,
            List<String> roles,
            List<CastingType> castingTypes,
            List<Genre> genres) {

        this.title = title;
        this.synopsis = synopsis;
        this.runningTime = runningTime;
        this.rating = rating;
        this.posterUrls = posterUrls;
        this.releaseDate = releaseDate;
        this.distributorName = distributorName;
        this.director = director;
        this.screenTypes = screenTypes;
        this.setMovieCasts(actors, roles, castingTypes);
        this.genres = genres;
    }

    public void setMovieCasts(List<Actor> actors, List<String> roles, List<CastingType> castingTypes) {

        if (actors == null || roles == null || castingTypes == null) {
            throw new IllegalArgumentException("actors, roles, and castingTypes lists cannot be null.");
        }

        if (actors.size() != roles.size() || actors.size() != castingTypes.size()) {
            throw new IllegalArgumentException("actors, roles, and castingTypes lists must have the same size.");
        }
        
        this.movieCasts = new ArrayList<>();
        for (int i = 0; i < actors.size(); i++) {
            MovieCast movieCast = new MovieCast(this, actors.get(i), roles.get(i), castingTypes.get(i));
            this.movieCasts.add(movieCast);
        }
    }

    public boolean isReleased(LocalDate date) {
        return this.releaseDate.isBefore(date);
    }

    public boolean isUpcoming(LocalDate date) {
        return this.releaseDate.isAfter(date);
    }
}

