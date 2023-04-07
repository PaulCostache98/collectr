package com.gamification.collectr.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter
public class LongListConverter implements AttributeConverter<List<Long>, String> {

    @Override
    public String convertToDatabaseColumn(List<Long> list) {
        if(list.isEmpty()) {
            return " ";
        }
        if(list.size() == 1) {
            return String.valueOf(list.toString().charAt(1));
        }
        return String.join(",", list.toString());

    }

    @Override
    public List<Long> convertToEntityAttribute(String joined) {
        if(joined.equals(" ")) {
            return new ArrayList<>();
        }
        if(joined.length() == 1) {
            return new ArrayList<>(List.of(Long.parseLong(joined)));
        }
        List<Long> longTemp = new ArrayList<>();
        for(int i=1;i<joined.length();i+=3) {
            longTemp.add(Long.parseLong(String.valueOf(joined.toCharArray()[i])));
        }
        return longTemp;
    }

}
