package com.lessons.home.database.types;

import com.lessons.home.database.types.interfaces.Type;

public class Dbl implements Type<Double> {
    public Dbl() {
    }

    public Double getValue(String rawValue) {
        try {
            return Double.parseDouble(rawValue);
        } catch (Exception ex) {
            System.out.println("Value is not Double");
            throw new RuntimeException("Это не Double");
        }
    }
}
