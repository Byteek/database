package com.lessons.home.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Database {

    private static final List<Map<String, Object>> storeCash = new ArrayList<Map<String, Object>>();
    private static final Types TYPES = new Types();

    public static void main(String[] args) {
        String INSERT = "INSERT VALUES ‘lastName’ = ‘Федоров’ , ‘id’=3, ‘age’=40, ‘active’=true";
        String UPDATE = "UPDATE VALUES ‘active’=false, ‘cost’=10.1 where ‘id’=3";
        String UPDATE_ALL = "UPDATE VALUES ‘active’=true where ‘active’=false";
        String SELECT = "SELECT WHERE ‘age’>=30 and ‘lastName’ ilike ‘%п%";
        String DELETE = "DELETE WHERE ‘id’=3";

        execute(INSERT);
//        execute(UPDATE);
    }

    private static void execute(String command) {
        if (command.startsWith("INSERT VALUES")) {
            List<Map<String, Object>> insertedValues = executeInsert(command);

            insertedValues.forEach(System.out::println);
        }

        if (command.startsWith("UPDATE VALUES")) {
            List<Map<String, Object>> updatedValues = executeUpdate(command);

            updatedValues.forEach(System.out::println);
        }


    }

    private static List<Map<String, Object>> executeInsert(String command) {
        String[] insertValues = command
                .replace("INSERT VALUES", "")
                .trim()
                .replaceAll(" ", "")
                .split(",");

        Map<String, Object> object = new HashMap<>();

        for (String insertValue : insertValues) {
            String[] keyValue = insertValue.split("=");

            String key = keyValue[0].replaceAll("['|’]", "");
            Type type = TYPES.USER.get(key);
            Object value = type.getValue(keyValue[1]);

            object.put(key, value);
        }

        storeCash.add(object);

        return List.of(object);
    }

    private static List<Map<String, Object>> executeUpdate(String command) {
        String[] updateFieldsWithWhere = command
                .replace("UPDATE VALUES", "")
                .trim()
                .replaceAll(" ", "")
                .split("[wW][hH][eE][rR][eE]");

        Arrays.stream(updateFieldsWithWhere).forEach(System.out::println);

        String[] updateFields = updateFieldsWithWhere[0].split(",");
        String where = updateFieldsWithWhere[1];

        System.out.println("where");
        System.out.println(where);


        System.out.println("UpdatedFields");
        Arrays.stream(updateFields).forEach(System.out::println);

        return List.of(null);
    }

    private static Object getInsertValue(String rawValue) {
        if (Objects.isNull(rawValue) || rawValue.indexOf("null") > 0) {
            return null;
        }

        if (rawValue.indexOf("'") >= 0 && rawValue.lastIndexOf("'") > 0) {
            return rawValue.replaceAll("'", "");
        }

        if (rawValue.startsWith("true") || rawValue.startsWith("false")) {
            return Boolean.valueOf(rawValue);
        }

        try {
            return Integer.parseInt(rawValue);
        } catch (Exception ex) {
            System.out.println("String is not Integer");
        }

        try {
            return Double.parseDouble(rawValue);
        } catch (Exception ex) {
            System.out.println("String is not Double");
        }

        return null;
    }
}
