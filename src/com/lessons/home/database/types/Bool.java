package com.lessons.home.database.types;

import com.lessons.home.database.types.interfaces.Type;

public class Bool implements Type<Boolean> {
    public Bool() {
    }

    public Boolean getValue(String rawValue) {
        if (rawValue.startsWith("true") || rawValue.startsWith("false")) {
            return Boolean.valueOf(rawValue);
        }else {
            System.out.println("Value is not Boolean");
            throw new RuntimeException("Это не Boolean");
        }
    }
}