package com.uos.dsd.cinema.domain.theater;

import com.uos.dsd.cinema.common.model.Base;
import com.uos.dsd.cinema.domain.screen_type.ScreenType;
import com.uos.dsd.cinema.domain.theater.enums.LayoutElement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Lob;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "theaters")
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Theater extends Base {

    @Id
    private Long id;

    private String name;

    @Lob
    @Column(columnDefinition = "CLOB")
    @Convert(converter = LayoutConverter.class)
    private List<List<Integer>> layout;

    @OneToMany(mappedBy = "theater", fetch = FetchType.LAZY)
    private List<TheaterSeat> seats;

    @ManyToMany
    @JoinTable(
        name = "theater_screen_types",
        joinColumns = @JoinColumn(name = "theater_id"),
        inverseJoinColumns = @JoinColumn(name = "screen_type")
    )
    private List<ScreenType> screenTypes;

    public Theater(
            Long id, 
            String name, 
            List<List<Integer>> layout,
            List<ScreenType> screenTypes) {

        super();
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id is required");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (screenTypes == null || screenTypes.isEmpty()) {
            throw new IllegalArgumentException("Screen types are required");
        }
        validateLayout(layout);
        this.id = id;
        this.name = name;
        this.layout = layout;
        this.seats = generateSeats();
        this.screenTypes = screenTypes;
    }

    public void validateLayout(List<List<Integer>> layout) {

        if (layout == null || layout.isEmpty()) {
            throw new IllegalArgumentException("Layout is required");
        }
        for (List<Integer> row : layout) {
            if (row == null || row.isEmpty()) {
                throw new IllegalArgumentException("Layout is required");
            }
            for (Integer seat : row) {
                if (seat == null || (seat < 0 && seat > 2)) {
                    throw new IllegalArgumentException("Layout element is invalid");
                }
            }
        }
    }

    public List<TheaterSeat> generateSeats() {

        List<TheaterSeat> seats = new ArrayList<>();
        char row = 'A';
        int column = 0;

        for (int i = 0; i < layout.size(); i++) {
            for (int j = 0; j < layout.get(i).size(); j++) {
                if (layout.get(i).get(j) == 1) {
                    column++;
                    seats.add(new TheaterSeat(id, row + "" + column, true));
                }
            }
            if (column != 0) {
                row++;
                column = 0;
            }
        }

        return seats;
    }

    public List<List<LayoutElement>> getLayout() {

        return layout.stream()
            .map(row -> row.stream()
                .map(LayoutElement::of)
                .collect(Collectors.toList()))
            .collect(Collectors.toList());
    }
}