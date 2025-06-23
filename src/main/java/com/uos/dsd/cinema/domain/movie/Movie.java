package com.uos.dsd.cinema.domain.movie;

import com.uos.dsd.cinema.common.converter.StringListConverter;
import com.uos.dsd.cinema.common.model.Base;
import com.uos.dsd.cinema.domain.genre.Genre;
import com.uos.dsd.cinema.domain.director.Director;
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
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "movies")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NamedEntityGraph(name = "Movie.withAll", attributeNodes = {
    @NamedAttributeNode("director"),
    @NamedAttributeNode(value = "movieCasts", subgraph = "movieCasts-subgraph"),
    @NamedAttributeNode("genres"),
    @NamedAttributeNode("screenTypes")
}, subgraphs = {
    @NamedSubgraph(name = "movieCasts-subgraph", attributeNodes = {
        @NamedAttributeNode("actor")
    })
})
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

    @Column(name="distributor", nullable = false)
    private String distributor;

    @Column(name = "director_id")
    private Long directorId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "director_id", insertable = false, updatable = false)
    private Director director;

    @Column(name = "cumulative_bookings")
    private int cumulativeBookings;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieCast> movieCasts = new ArrayList<>();

    @Lob
    @Convert(converter = StringListConverter.class)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "movie_screen_types",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "screen_type"))
    private Set<ScreenType> screenTypes;

    @Lob
    @Convert(converter = StringListConverter.class)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "movie_genres",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_name"))
    private Set<Genre> genres;

    public Movie(String title,
            String synopsis,
            Integer runningTime,
            MovieRating rating,
            List<String> posterUrls,
            LocalDate releaseDate,
            String distributor,
            Long directorId,
            Set<ScreenType> screenTypes,
            List<Casting> castings,
            Set<Genre> genres) {
        super();

        this.title = title;
        this.synopsis = synopsis;
        this.runningTime = runningTime;
        this.rating = rating;
        this.posterUrls = posterUrls;
        this.releaseDate = releaseDate;
        this.distributor = distributor;
        this.directorId = directorId;
        this.screenTypes = screenTypes;
        this.setMovieCasts(castings);
        this.genres = genres;
    }

    public void update(String title,
            String synopsis,
            Integer runningTime,
            MovieRating rating,
            List<String> posterUrls,
            LocalDate releaseDate,
            String distributor,
            Long directorId,
            Set<ScreenType> screenTypes,
            List<Casting> castings,
            Set<Genre> genres) {

        this.title = title;
        this.synopsis = synopsis;
        this.runningTime = runningTime;
        this.rating = rating;
        this.posterUrls = posterUrls;
        this.releaseDate = releaseDate;
        this.distributor = distributor;
        this.directorId = directorId;
        this.screenTypes.clear();
        this.screenTypes = screenTypes;
        this.movieCasts.clear();
        this.setMovieCasts(castings);
        this.genres.clear();
        this.genres = genres;
    }

    public void setMovieCasts(List<Casting> castings) {
        for (Casting casting : castings) {
            MovieCast movieCast = new MovieCast(this, casting);
            this.movieCasts.add(movieCast);
        }
    }

    public void updateCumulativeBookings(int cumulativeBookings) {
        this.cumulativeBookings = cumulativeBookings;
    }

    public boolean isReleased(LocalDate date) {
        return this.releaseDate.isBefore(date);
    }

    public boolean isUpcoming(LocalDate date) {
        return this.releaseDate.isAfter(date);
    }

    public void delete() {
        super.delete();
        this.screenTypes.clear();
        this.movieCasts.clear();
        this.genres.clear();
    }
}

