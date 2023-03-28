package com.lessons.home.database;

interface Type<T> {
    T getValue(String rawValue);
}