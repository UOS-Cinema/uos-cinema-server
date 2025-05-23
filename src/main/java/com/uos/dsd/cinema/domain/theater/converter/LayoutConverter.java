package com.uos.dsd.cinema.domain.theater.converter;

import com.uos.dsd.cinema.domain.theater.enums.LayoutElement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class LayoutConverter implements AttributeConverter<List<List<LayoutElement>>, String> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<List<LayoutElement>> layout) {

        try {
            return mapper.writeValueAsString(convertToIntegerLayout(layout));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize layout", e);
        }
    }

    @Override
    public List<List<LayoutElement>> convertToEntityAttribute(String dbData) {

        try {
            JavaType type = mapper.getTypeFactory().constructCollectionType(List.class,
                    mapper.getTypeFactory().constructCollectionType(List.class, Integer.class));
            return convertToLayoutElementLayout(mapper.readValue(dbData, type));
        } catch (IOException e) {
            throw new RuntimeException("Failed to deserialize layout", e);
        }
    }
    
    private List<List<Integer>> convertToIntegerLayout(List<List<LayoutElement>> layout) {

        return layout.stream()
                .map(row -> row.stream().map(LayoutElement::getCode).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
    
    private List<List<LayoutElement>> convertToLayoutElementLayout(List<List<Integer>> layout) {

        return layout.stream()
                .map(row -> row.stream().map(LayoutElement::of).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
}
