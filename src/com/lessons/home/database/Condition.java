package com.lessons.home.database;

import java.util.Objects;

import static com.lessons.home.database.Utils.getString;

public class Condition {

    Conditions operator;
    String key;
    String value;

    public Condition(Conditions operator, String key, String value) {
        this.operator = operator;
        this.key = key;
        this.value = value;
    }

    public static Condition getCondition(String condition) {
        Condition conditionObj = null;

        if (condition.contains("=")) {
            String[] split = condition.split("=");
            conditionObj = new Condition(Conditions.EQUALS, getString(split[0]), split[1]);
        }

        if (condition.contains("!=")) {
            String[] split = condition.split("!=");
            conditionObj = new Condition(Conditions.NOT_EQUALS, getString(split[0]), split[1]);
        }

        if (condition.contains("like")) {
            String[] split = condition.split("like");
            conditionObj = new Condition(Conditions.LIKE, getString(split[0]), split[1]);
        }

        if (condition.contains("ilike")) {
            String[] split = condition.split("ilike");
            conditionObj = new Condition(Conditions.LIKE_INCENTIVE, getString(split[0]), split[1]);
        }

        if (condition.contains(">=")) {
            String[] split = condition.split(">=");
            conditionObj = new Condition(Conditions.GTE, getString(split[0]), split[1]);
        }

        if (condition.contains("<=")) {
            String[] split = condition.split("<=");
            conditionObj = new Condition(Conditions.LTE, getString(split[0]), split[1]);
        }

        if (condition.contains(">")) {
            String[] split = condition.split(">");
            conditionObj = new Condition(Conditions.GT, getString(split[0]), split[1]);
        }
        if (condition.contains("<")) {
            String[] split = condition.split("<");
            conditionObj = new Condition(Conditions.LT, getString(split[0]), split[1]);
        }

        if (Objects.isNull(conditionObj)) {
            throw new RuntimeException("Плохой кандишен");
        }

        return conditionObj;
    }

    public Conditions getOperator() {
        return operator;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
