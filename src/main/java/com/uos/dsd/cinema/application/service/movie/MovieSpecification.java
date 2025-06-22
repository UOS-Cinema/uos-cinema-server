package com.uos.dsd.cinema.application.service.movie;

import com.uos.dsd.cinema.domain.movie.Movie;
import com.uos.dsd.cinema.domain.movie.MovieCast;
import com.uos.dsd.cinema.domain.actor.Actor;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.*;
import java.time.LocalDate;
import java.util.List;

public class MovieSpecification {

    public static Specification<Movie> notDeleted() {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.isNull(root.get("deletedAt"));
    }

    public static Specification<Movie> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null || title.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("title"), "%" + title + "%");
        };
    }

    public static Specification<Movie> hasDirector(String directorName) {
        return (root, query, criteriaBuilder) -> {
            if (directorName == null || directorName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("director").get("name"), "%" + directorName + "%");
        };
    }

    public static Specification<Movie> hasActor(String actorName) {
        return (root, query, cb) -> {
            if (actorName == null || actorName.isEmpty()) {
                return cb.conjunction();
            }

            // movie → movieCasts
            Join<Movie, MovieCast> movieCastJoin = root.join("movieCasts", JoinType.INNER);
            // movieCasts → actor
            Join<MovieCast, Actor> actorJoin = movieCastJoin.join("actor", JoinType.INNER);

            return cb.like(actorJoin.get("name"), "%" + actorName + "%");
        };
    }

    public static Specification<Movie> hasReleaseDateBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate == null && endDate == null) {
                return criteriaBuilder.conjunction();
            }
            
            Predicate predicate = criteriaBuilder.conjunction();
            if (startDate != null) {
                predicate = criteriaBuilder.and(predicate, 
                    criteriaBuilder.greaterThanOrEqualTo(root.get("releaseDate"), startDate));
            }
            if (endDate != null) {
                predicate = criteriaBuilder.and(predicate, 
                    criteriaBuilder.lessThanOrEqualTo(root.get("releaseDate"), endDate));
            }
            return predicate;
        };
    }

    public static Specification<Movie> hasGenres(List<String> genres) {
        return (root, query, criteriaBuilder) -> {
            if (genres == null || genres.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            
            Join<Movie, Object> genreJoin = root.join("genres");
            return genreJoin.get("name").in(genres);
        };
    }

    public static Specification<Movie> hasScreenTypes(List<String> screenTypes) {
        return (root, query, criteriaBuilder) -> {
            if (screenTypes == null || screenTypes.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Join<Movie, Object> screenTypeJoin = root.join("screenTypes");
            return screenTypeJoin.get("type").in(screenTypes);
        };
    }
} 