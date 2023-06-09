package com.lessons.home.database.types;

import com.lessons.home.database.types.interfaces.Type;

public class BooleanType implements Type<Boolean> {
    public BooleanType() {
    }

    public Boolean getValue(String rawValue) {
        if (rawValue.startsWith("true") || rawValue.startsWith("false")) {
            String trim = rawValue.trim().replaceAll(" ", "");

            return Boolean.valueOf(trim);
        } else {
            System.out.println("Value is not Boolean");
            throw new RuntimeException("Это не Boolean");
        }
    }
}