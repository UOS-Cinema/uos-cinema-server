package com.uos.dsd.cinema.domain.movie;

import com.uos.dsd.cinema.common.model.Base;
import com.uos.dsd.cinema.domain.screen_type.ScreenType;
import com.uos.dsd.cinema.core.converter.StringListConverter;

import lombok.Getter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.Convert;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "movies")
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, of = "id")
@NoArgsConstructor
public class Movie extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String synopsis;

    @Convert(converter = StringListConverter.class)
    private List<String> posterUrls;

    private int runningTime;

    @Enumerated(EnumType.STRING)
    private MovieRating rating;

    private LocalDate releaseDate;

    private String distributor;

    private Long directorId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie_screen_types",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "screen_type"))
    private List<ScreenType> screenTypes;

    public Movie(String title,
                String synopsis,
                List<String> posterUrls,
                int runningTime,
                MovieRating rating,
                LocalDate releaseDate,
                String distributor,
                Long directorId,
                List<ScreenType> screenTypes) {

        this.title = title;
        this.synopsis = synopsis;
        this.posterUrls = posterUrls;
        this.runningTime = runningTime;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.distributor = distributor;
        this.directorId = directorId;
        this.screenTypes = screenTypes;
    }
}
