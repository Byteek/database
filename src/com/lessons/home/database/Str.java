package com.lessons.home.database;

class Str implements Type<String> {
    public Str() {
    }

    public String getValue(String rawValue) {
        if (rawValue.indexOf("'") >= 0 && rawValue.lastIndexOf("'") > 0) {
            return rawValue.replaceAll("'", "");
        } else {
            System.out.println("Value is not String");
            throw new RuntimeException("Это не строка");
        }
    }
}
