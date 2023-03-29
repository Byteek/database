package com.lessons.home.database;

import com.lessons.home.database.types.Tables;
import com.lessons.home.database.types.interfaces.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.lessons.home.database.Utils.getString;

public class Database {

    private static final List<Map<String, Object>> storeCash = new ArrayList<Map<String, Object>>();

    public static void main(String[] args) {
        String INSERT = "INSERT VALUES ‘lastName’ = ‘Федоров’ , ‘id’=1, ‘age’=40, ‘active’=false";
        String INSERT1 = "INSERT VALUES ‘lastName’ = ‘Сидоров’ , ‘id’=2, ‘age’=41, ‘active’=false";
        String INSERT2 = "INSERT VALUES ‘lastName’ = ‘Иванов’ , ‘id’=3, ‘age’=42, ‘active’=false";
        String INSERT3 = "INSERT VALUES ‘lastName’ = ‘Петров’ , ‘id’=4, ‘age’=43, ‘active’=false";
        String INSERT4 = "INSERT VALUES ‘lastName’ = ‘Лошара’ , ‘id’=5, ‘age’=44, ‘active’=true";
        String UPDATE = "UPDATE VALUES ‘active’=false, ‘cost’=10.1 where ‘id’=3";
        String UPDATE_ALL = "UPDATE VALUES ‘active’=true where ‘active’=false";
        String SELECT = "SELECT WHERE ‘age’>=30 and ‘lastName’ ilike ‘%п%";
        String DELETE = "DELETE WHERE ‘id’=3";

        execute(INSERT);
        execute(INSERT1);
        execute(INSERT2);
        execute(INSERT3);
        execute(INSERT4);

        execute(UPDATE);
        execute(UPDATE_ALL);
    }

    private static void execute(String command) {
        if (command.trim().toUpperCase().startsWith("INSERT VALUES")) {
            List<Map<String, Object>> insertedValues = executeInsert(command);

            System.out.println("INSERT VALUES");
            insertedValues.forEach(System.out::println);
        }

        if (command.trim().toUpperCase().startsWith("UPDATE VALUES")) {
            List<Map<String, Object>> updatedValues = executeUpdate(command);

            System.out.println("UPDATE VALUES");
            updatedValues.forEach(System.out::println);
        }
    }

    private static List<Map<String, Object>> executeInsert(String command) {
        List<String> insertValues = Arrays
                .stream(command.replace("INSERT VALUES", "")
                        .trim()
                        .split(","))
                .map(String::trim)
                .collect(Collectors.toList());


        Map<String, Object> object = getValues(insertValues);

        storeCash.add(object);

        return List.of(object);
    }

    private static Map<String, Object> getValues(List<String> insertValues) {
        Map<String, Object> values = new HashMap<>();

        for (String insertFields : insertValues) {
            List<String> keyValueArray = Arrays
                    .stream(insertFields.split("="))
                    .map(String::trim)
                    .collect(Collectors.toList());

            String rawKey = keyValueArray.get(0);
            String rawValue = keyValueArray.get(1);

            String key = getString(rawKey);

            if (Tables.USER.containsKey(key)) {
                Type<?> type = Tables.USER.get(key);
                Object value = type.getValue(rawValue);
                values.put(key, value);
            }
        }

        return values;
    }

    //        String UPDATE = "UPDATE VALUES ‘active’=false, ‘cost’=10.1 where ‘id’=3";
    private static List<Map<String, Object>> executeUpdate(String command) {
        List<String> updateFieldsWithWhere = Arrays.stream(command
                        .replace("UPDATE VALUES", "")
                        .trim()
                        .split("[wW][hH][eE][rR][eE]"))
                .map(String::trim)
                .collect(Collectors.toList());

        String valuesToSet = updateFieldsWithWhere.get(0); //‘active’=false, ‘cost’=10.1
        String where = updateFieldsWithWhere.get(1); //‘id’=3

        Map<String, Object> values = getValues(Arrays
                .stream(valuesToSet
                        .split(","))
                .map(String::trim)
                .collect(Collectors.toList()));

        String[] split = where.split("=");
        String whereKey = getString(split[0]); //id
        Object whereValue = Tables.USER.get(whereKey).getValue(split[1]); //3

        List<Map<String, Object>> updatedObjects = new ArrayList<>();

        storeCash.forEach(stringObjectMap -> {
            if(stringObjectMap.get(whereKey).equals(whereValue)){
                stringObjectMap.putAll(values);

                updatedObjects.add(stringObjectMap);
            }
        });


        return updatedObjects;
    }
}
