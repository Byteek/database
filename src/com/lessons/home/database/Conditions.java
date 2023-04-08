package com.lessons.home.database;

import com.lessons.home.database.types.DoubleType;
import com.lessons.home.database.types.LongType;

import java.util.List;

public enum Conditions {
    EQUALS("=", List.of(Boolean.class, String.class, Long.class, Double.class)),
    NOT_EQUALS("!=", List.of(Boolean.class, String.class, Long.class, Double.class)),
    LIKE("like", List.of(String.class)),
    LIKE_INCENTIVE("ilike", List.of(String.class)),
    GTE(">=", List.of(Long.class, Double.class)),
    GT(">", List.of(Long.class, Double.class)),
    LTE("<=", List.of(Long.class, Double.class)),
    LT("<", List.of(Long.class, Double.class));

    private final String value;
    private final List<Class<?>> supportedTypes;

    Conditions(String value, List<Class<?>> supportedTypes) {
        this.value = value;
        this.supportedTypes = supportedTypes;
    }

    public String getValue() {
        return value;
    }

    public List<Class<?>> getSupportedTypes() {
        return supportedTypes;
    }

    public boolean compare(Object a, Object b) {
        boolean typeOfClassesIsSupported = this.supportedTypes.contains(a.getClass()) && this.supportedTypes.contains(b.getClass());

        if (typeOfClassesIsSupported) {
            String stringA = String.valueOf(a);
            String stringB = String.valueOf(b);

            boolean isObjectsLong = a instanceof LongType && b instanceof LongType;
            boolean isObjectsDouble = a instanceof DoubleType && b instanceof DoubleType;

            switch (this.value) {
                case "=" -> {
                    return a.equals(b);
                }
                case "!=" -> {
                    return !a.equals(b);
                }
                case "like" -> {
                    return stringA.compareTo(String.valueOf(b)) == 0;
                }
                case "ilike" -> {
                    return stringA.compareToIgnoreCase(String.valueOf(b)) == 0;
                }
                case ">=" -> {
                    long longA = Long.parseLong(stringA);
                    long longB = Long.parseLong(stringB);

                    double doubleA = Double.parseDouble(stringA);
                    double doubleB = Double.parseDouble(stringB);

                    if (isObjectsLong) {
                        return longA >= longB;
                    }
                    if (isObjectsDouble) {
                        return doubleA >= doubleB;
                    }

                    return false;
                }
                case "<=" -> {
                    long longA = Long.parseLong(stringA);
                    long longB = Long.parseLong(stringB);

                    double doubleA = Double.parseDouble(stringA);
                    double doubleB = Double.parseDouble(stringB);

                    if (isObjectsLong) {
                        return longA <= longB;
                    }
                    if (isObjectsDouble) {
                        return doubleA <= doubleB;
                    }

                    return false;

                }
                case ">" -> {
                    long longA = Long.parseLong(stringA);
                    long longB = Long.parseLong(stringB);

                    double doubleA = Double.parseDouble(stringA);
                    double doubleB = Double.parseDouble(stringB);

                    if (isObjectsLong) {
                        return longA > longB;
                    }
                    if (isObjectsDouble) {
                        return doubleA > doubleB;
                    }

                    return false;
                }
                case "<" -> {
                    long longA = Long.parseLong(stringA);
                    long longB = Long.parseLong(stringB);

                    double doubleA = Double.parseDouble(stringA);
                    double doubleB = Double.parseDouble(stringB);

                    if (isObjectsLong) {
                        return longA < longB;
                    }
                    if (isObjectsDouble) {
                        return doubleA < doubleB;
                    }

                    return false;
                }
            }
        }

        return false;
    }

}
