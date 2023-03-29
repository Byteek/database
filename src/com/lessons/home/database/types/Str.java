package com.lessons.home.database.types;

import com.lessons.home.database.types.interfaces.Type;

import static com.lessons.home.database.Utils.getString;

public class Str implements Type<String> {
    public Str() {
    }

    public String getValue(String rawValue) {
        if (rawValue.contains("'") || rawValue.contains("’")||rawValue.contains("‘")) {
            return getString(rawValue);
        } else {
            System.out.println("Value is not String");
            throw new RuntimeException("Это не строка");
        }
    }
}
