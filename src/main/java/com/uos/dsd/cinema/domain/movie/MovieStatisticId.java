package com.uos.dsd.cinema.domain.movie;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"statisticDate", "movieId"})
public class MovieStatisticId implements Serializable {

    @Column(name = "statistic_date")
    private LocalDate statisticDate;

    @Column(name = "movie_id")
    private Long movieId;
}
