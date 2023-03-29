package com.lessons.home.database.types;

import com.lessons.home.database.types.interfaces.Type;

import java.util.Map;

public class Tables {
    public static Map<String, Type> USER = Map.of(
            "id", new Lng(),
            "lastName", new Str(),
            "cost", new Dbl(),
            "age", new Lng(),
            "active", new Bool());
}
