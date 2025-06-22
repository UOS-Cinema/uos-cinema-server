package com.uos.dsd.cinema.domain.movie;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movie_statistics")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class MovieStatistics {

    @EmbeddedId
    private MovieStatisticId id;

    @Column(name = "cumulative_bookings")
    private int cumulativeBookings;

    public MovieStatistics(
            LocalDate statisticDate, 
            Movie movie,
            int cumulativeBookings) {
        this.id = new MovieStatisticId(statisticDate, movie.getId());
        this.cumulativeBookings = cumulativeBookings;
    }
}
