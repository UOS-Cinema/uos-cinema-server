package com.uos.dsd.cinema.domain.theater;

import org.springframework.data.domain.Persistable;

import com.uos.dsd.cinema.common.model.Base;
import com.uos.dsd.cinema.domain.screen_type.ScreenType;
import com.uos.dsd.cinema.domain.theater.converter.LayoutConverter;
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
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "theaters")
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, of = "number")
@NoArgsConstructor
public class Theater extends Base implements Persistable<Long> {

    @Id
    @Column(name = "id")
    private Long number;

    private String name;

    @Lob
    @Column(columnDefinition = "CLOB")
    @Convert(converter = LayoutConverter.class)
    private List<List<LayoutElement>> layout;

    @OneToMany(mappedBy = "theater",
            fetch = FetchType.LAZY, 
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, 
            orphanRemoval = true)
    private List<TheaterSeat> seats;

    @ManyToMany
    @JoinTable(
        name = "theater_screen_types",
        joinColumns = @JoinColumn(name = "theater_id"),
        inverseJoinColumns = @JoinColumn(name = "screen_type")
    )
    private List<ScreenType> screenTypes;

    public Theater(
            Long number, 
            String name, 
            List<List<LayoutElement>> layout,
            List<ScreenType> screenTypes) {

        super();
        if (number == null || number <= 0) {
            throw new IllegalArgumentException("Number is required");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (screenTypes == null || screenTypes.isEmpty()) {
            throw new IllegalArgumentException("Screen types are required");
        }
        validateLayout(layout);
        this.number = number;
        this.name = name;
        this.layout = layout;
        this.screenTypes = screenTypes;
        this.seats = generateSeats();
    }

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }

    @Override
    public Long getId() {
        return this.number;
    }

    public void validateLayout(List<List<LayoutElement>> layout) {

        if (layout == null || layout.isEmpty()) {
            throw new IllegalArgumentException("Layout is required");
        }
        for (List<LayoutElement> row : layout) {
            if (row == null || row.isEmpty()) {
                throw new IllegalArgumentException("Layout is required");
            }
        }
    }

    public void validateScreenTypes(List<ScreenType> screenTypes) {
        if (screenTypes == null || screenTypes.isEmpty()) {
            throw new IllegalArgumentException("Screen types are required");
        }
    }

    private List<TheaterSeat> generateSeats() {

        List<TheaterSeat> seats = new ArrayList<>();
        char row = 'A';
        int column = 0;

        for (int i = 0; i < layout.size(); i++) {
            for (int j = 0; j < layout.get(i).size(); j++) {
                if (layout.get(i).get(j) == LayoutElement.SEAT) {
                    column++;

                    TheaterSeat seat = new TheaterSeat(this.number, row + "" + column, true);
                    seat.setTheater(this);
                    seats.add(seat);
                }
            }
            if (column != 0) {
                row++;
                column = 0;
            }
        }
        return seats;
    }

    public void modifyName(String name) {

        this.name = name;
    }

    public void deleteSeats() {
        this.seats.clear();
    }

    public void modifyLayout(List<List<LayoutElement>> layout) {

        validateLayout(layout);
        this.layout = layout;
        this.seats.clear();
        this.seats = new ArrayList<>();
        this.seats.addAll(generateSeats());
    }

    public void modifyScreenTypes(List<ScreenType> screenTypes) {

        validateScreenTypes(screenTypes);
        this.screenTypes.clear();
        this.screenTypes.addAll(screenTypes);
    }
}