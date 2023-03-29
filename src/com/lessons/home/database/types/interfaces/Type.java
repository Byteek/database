package com.lessons.home.database.types.interfaces;

public interface Type<T> {
    T getValue(String rawValue);
}