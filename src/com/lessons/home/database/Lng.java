package com.lessons.home.database;

class Lng implements Type<Long> {
    public Lng() {
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