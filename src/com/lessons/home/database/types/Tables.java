package com.lessons.home.database.types;

import com.lessons.home.database.types.interfaces.Type;

import java.util.Map;

public class Tables {
    public static Map<String, Type> USER = Map.of(
            "id", new LongType(),
            "lastName", new StringType(),
            "cost", new DoubleType(),
            "age", new LongType(),
            "active", new BooleanType());
}
