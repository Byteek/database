package com.lessons.home.database.types;

import com.lessons.home.database.types.interfaces.Type;

public class LongType implements Type<Long> {
    public LongType() {
    }

    public Long getValue(String rawValue) {
        try {
            return Long.parseLong(rawValue);
        } catch (Exception ex) {
            System.out.println("Value is not Long");
            throw new RuntimeException("Это не Long");
        }
    }
}