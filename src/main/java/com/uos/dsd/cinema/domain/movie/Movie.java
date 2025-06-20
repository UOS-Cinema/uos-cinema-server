package com.uos.dsd.cinema.domain.movie;

import com.uos.dsd.cinema.common.model.Base;
import com.uos.dsd.cinema.domain.movie.enums.CastingType;
import com.uos.dsd.cinema.domain.movie.enums.MovieRating;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
@Getter
@Setter
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
    private Long runningTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovieRating rating;

    @Lob
    @Column(columnDefinition = "CLOB")
    private String posterUrls;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(nullable = false)
    private String distributorName;

    @Column(nullable = false)
    private Long directorId;

    // 생명주기 관리
    @OneToMany(mappedBy = "id.movie", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieScreenType> movieScreenTypes;

    // 생명주기 관리
    @OneToMany(mappedBy = "id.movie", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieCast> movieCasts;

    // 생명주기 관리
    @OneToMany(mappedBy = "id.movie", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieGenre> movieGenres;

    public Movie(String title,
            String synopsis,
            Long runningTime,
            MovieRating rating,
            String posterUrls,
            LocalDate releaseDate,
            String distributorName,
            Long directorId,
            List<String> screenTypeNames,
            List<Long> actorIds,
            List<String> roles,
            List<CastingType> castingTypes,
            List<String> genreNames) {

        this.title = title;
        this.synopsis = synopsis;
        this.runningTime = runningTime;
        this.rating = rating;
        this.setPosterUrls(posterUrls);
        this.releaseDate = releaseDate;
        this.distributorName = distributorName;
        this.directorId = directorId;
        this.setMovieScreenTypes(screenTypeNames);
        this.setMovieCasts(actorIds, roles, castingTypes);
        this.setMovieGenres(genreNames);
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public void setPosterUrls(String posterUrls) {
        
        this.posterUrls = posterUrls;
        if (this.posterUrls == null || this.posterUrls.isEmpty()) {
            this.posterUrls = "{}";
        }
    }

    public void setMovieScreenTypes(List<String> screenTypeNames) {
        
        this.movieScreenTypes = new ArrayList<>();
        if (screenTypeNames == null || screenTypeNames.isEmpty()) {
            return;
        }
        
        this.movieScreenTypes = screenTypeNames.stream()
                .map(screenTypeName -> new MovieScreenType(this, screenTypeName))
                .toList();
    }

    public void setMovieCasts(List<Long> actorIds, List<String> roles, List<CastingType> castingTypes) {

        this.movieCasts = new ArrayList<>();

        // 모두가 null인 경우는 문제가 없음
        if (actorIds == null && roles == null && castingTypes == null) {
            return;
        }
        // List의 크기가 다르면 예외 발생
        if (actorIds == null || roles == null || castingTypes == null ||
            actorIds.size() != roles.size() || actorIds.size() != castingTypes.size()) {
            throw new IllegalArgumentException("Actor IDs, roles, and casting types must have the same size");
        }

        for (int i = 0; i < actorIds.size(); i++) {
            this.movieCasts.add(new MovieCast(this, actorIds.get(i), roles.get(i), castingTypes.get(i)));
        }
    }

    public void setMovieGenres(List<String> genreNames) {

        this.movieGenres = new ArrayList<>();
        if (genreNames == null || genreNames.isEmpty()) {
            return;
        }
        
        this.movieGenres = genreNames.stream()
                .map(genreName -> new MovieGenre(this, genreName))
                .toList();
    }

    public boolean isReleased(LocalDate date) {

        return this.releaseDate.isBefore(date);
    }

    public boolean isUpcoming(LocalDate date) {

        return this.releaseDate.isAfter(date);
    }
}
