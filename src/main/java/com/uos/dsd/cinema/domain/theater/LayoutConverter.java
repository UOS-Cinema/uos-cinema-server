package com.uos.dsd.cinema.domain.theater;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.List;

@Converter
public class LayoutConverter implements AttributeConverter<List<List<Integer>>, String> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<List<Integer>> layout) {

        try {
            return mapper.writeValueAsString(layout);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize layout", e);
        }
    }

    @Override
    public List<List<Integer>> convertToEntityAttribute(String dbData) {

        try {
            JavaType type = mapper.getTypeFactory().constructCollectionType(List.class,
                mapper.getTypeFactory().constructCollectionType(List.class, Integer.class));
            return mapper.readValue(dbData, type);
        } catch (IOException e) {
            throw new RuntimeException("Failed to deserialize layout", e);
        }
    }
}
