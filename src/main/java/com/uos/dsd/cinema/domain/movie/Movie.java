package com.uos.dsd.cinema.domain.movie;

import com.uos.dsd.cinema.common.model.Base;
import com.uos.dsd.cinema.domain.screen_type.ScreenType;

import lombok.Getter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "movies")
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Movie extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_seq")
    @SequenceGenerator(name = "movie_seq", sequenceName = "movie_seq", allocationSize = 1)
    private Long id;

    private String title;

    private String synopsis;

    private String posterUrl;

    private int runningTime;

    @Enumerated(EnumType.STRING)
    private MovieRating rating;

    private LocalDate releaseDate;

    private String distributor;

    private String director;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie_screen_types",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "screen_type"))
    private List<ScreenType> screenTypes;

    public Movie(String title,
                String synopsis,
                String posterUrl,
                int runningTime,
                MovieRating rating,
                LocalDate releaseDate,
                String distributor,
                String director,
                List<ScreenType> screenTypes) {

        this.title = title;
        this.synopsis = synopsis;
        this.posterUrl = posterUrl;
        this.runningTime = runningTime;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.distributor = distributor;
        this.director = director;
        this.screenTypes = screenTypes;
    }
}
