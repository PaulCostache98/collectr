package com.gamification.collectr.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter
public class IntegerListConverter implements AttributeConverter<List<Integer>, String> {

    @Override
    public String convertToDatabaseColumn(List<Integer> list) {
        if(list == null || list.isEmpty()) {
            return " ";
        }
        if(list.size() == 1) {
            return String.valueOf(list.toString().charAt(1));
        }
        return String.join(",", list.toString());

    }

    @Override
    public List<Integer> convertToEntityAttribute(String joined) {
        if(joined.equals(" ")) {
            return new ArrayList<>();
        }
        if(joined.length() == 1) {
            return new ArrayList<>(List.of(Integer.parseInt(joined)));
        }
        List<Integer> integerTemp = new ArrayList<>();
        for(int i=1;i<joined.length();i+=3) {
            integerTemp.add(Integer.parseInt(String.valueOf(joined.toCharArray()[i])));
        }
        return integerTemp;
    }

}
