package com.lessons.home.database.types;

import com.lessons.home.database.types.interfaces.Type;

public class DoubleType implements Type<Double> {
    public DoubleType() {
    }

    public Double getValue(String rawValue) {
        try {
            String trim = rawValue.trim().replaceAll(" ", "");
            return Double.parseDouble(trim);
        } catch (Exception ex) {
            System.out.println("Value is not Double");
            throw new RuntimeException("Это не Double");
        }
    }
}
