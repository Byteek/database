package com.lessons.home.database;

import com.lessons.home.database.types.Tables;
import com.lessons.home.database.types.interfaces.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.lessons.home.database.Utils.getString;

public class Database {

    private static final List<Map<String, Object>> storeCash = new ArrayList<>();

    public static void main(String[] args) {
        String INSERT = "INSERT VALUES ‘lastName’ = ‘Федоров’ , ‘id’=1, ‘age’=40, ‘active’=false";
        String INSERT1 = "INSERT VALUES ‘lastName’ = ‘Сидоров’ , ‘id’=2, ‘age’=41, ‘active’=false";
        String INSERT2 = "INSERT VALUES ‘lastName’ = ‘Иванов’ , ‘id’=3, ‘age’=42, ‘active’=true";
        String INSERT3 = "INSERT VALUES ‘lastName’ = ‘Петров’ , ‘id’=4, ‘age’=43, ‘active’=false";
        String INSERT4 = "INSERT VALUES ‘lastName’ = ‘Лошара’ , ‘id’=5, ‘age’=44, ‘active’=true";
        String UPDATE = "UPDATE VALUES ‘active’=false, ‘cost’=10.1 where ‘id’=3 and 'age'=42";
        String UPDATE_ALL = "UPDATE VALUES ‘active’=true where ‘active’=false";
        String DELETE = "DELETE WHERE ‘id’=3";
        String SELECT = "SELECT WHERE ‘age’>=30 and ‘lastName’ ilike ‘%п%";

        execute(INSERT);
        execute(INSERT1);
        execute(INSERT2);
        execute(INSERT3);
        execute(INSERT4);

        storeCash.forEach(System.out::println);

        execute(UPDATE);
        System.out.println("Мы тут шота обновили");
        storeCash.forEach(System.out::println);

        execute(UPDATE_ALL);
        System.out.println("Мы тут шота обновили ALLLLLLLLLLL");
        storeCash.forEach(System.out::println);

        execute(DELETE);
        System.out.println("Мы тут шота удалили");
        storeCash.forEach(System.out::println);
    }

    private static void execute(String command) {
        if (command.trim().toUpperCase().startsWith("INSERT VALUES")) {
            List<Map<String, Object>> insertedValues = executeInsert(command);

            System.out.println("INSERT VALUES");
//            insertedValues.forEach(System.out::println);
        }

        if (command.trim().toUpperCase().startsWith("UPDATE VALUES")) {
            List<Map<String, Object>> updatedValues = executeUpdate(command);

            System.out.println("UPDATE VALUES");
//            updatedValues.forEach(System.out::println);
        }

        if (command.trim().toUpperCase().startsWith("DELETE")) {
            List<Map<String, Object>> deletedValues = executeDelete(command);

            System.out.println("DELETE");
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

    //String UPDATE = "UPDATE VALUES ‘active’=false, ‘cost’=10.1 where ‘id’=3";
    private static List<Map<String, Object>> executeUpdate(String command) {
        List<String> updateFieldsWithWhere = Arrays.stream(command
                        .replace("UPDATE VALUES", "")
                        .trim()
                        .split("[wW][hH][eE][rR][eE]"))
                .map(String::trim)
                .collect(Collectors.toList());

        Map<String, Object> values = fieldsToUpdate(updateFieldsWithWhere.get(0));

        String where = updateFieldsWithWhere.get(1); //‘id’=3 and age>30 and active=true

        List<Condition> whereConditions = getWhereConditions(where);

        List<Map<String, Object>> updatedObjects = new ArrayList<>();

        for (Map<String, Object> stringObjectMap : storeCash) {
            if (isObjectMatches(whereConditions, stringObjectMap)) {
                stringObjectMap.putAll(values);
                updatedObjects.add(stringObjectMap);
            }
        }
        return updatedObjects;
    }

    //String UPDATE = "UPDATE VALUES ‘active’=false, ‘cost’=10.1 where ‘id’=3";
    private static List<Map<String, Object>> executeDelete(String command) {
        List<String> updateFieldsWithWhere = Arrays.stream(command
                        .replace("DELETE", "")
                        .trim()
                        .split("[wW][hH][eE][rR][eE]"))
                .map(String::trim)
                .collect(Collectors.toList());

        String where = updateFieldsWithWhere.get(1); //‘id’=3 and age>30 and active=true

        List<Condition> whereConditions = getWhereConditions(where);

        List<Map<String, Object>> deletedObjects = new ArrayList<>();

        Iterator<Map<String, Object>> iterator = storeCash.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> stringObjectMap = iterator.next();

            if (isObjectMatches(whereConditions, stringObjectMap)) {
                iterator.remove();
                deletedObjects.add(stringObjectMap);
            }
        }

        return deletedObjects;
    }


    private static boolean isObjectMatches(List<Condition> whereConditions, Map<String, Object> stringObjectMap) {
        List<Condition> successConditions = getSuccessConditions(whereConditions, stringObjectMap);

        return whereConditions.size() == successConditions.size();
    }

    private static List<Condition> getSuccessConditions(List<Condition> whereConditions, Map<String, Object> stringObjectMap) {
        return whereConditions
                .stream()
                .filter(condition -> {
                    String key = condition.getKey();
                    Object value = Tables.USER.get(key).getValue(condition.getValue());
                    return condition.operator.compare(stringObjectMap.get(key), value);
                })
                .collect(Collectors.toList());
    }


    private static List<Condition> getWhereConditions(String wherePart) {
        //‘id’=3 and age>30 and active=true
        String[] andConditions = wherePart.split("[aA][nN][dD]");
        List<Condition> conditionList = new ArrayList<>();
        // [‘id’=3 , age>30 , active=true]
        for (String someCondition : andConditions) {
            Condition condition = Condition.getCondition(someCondition);
            conditionList.add(condition);
        }

        return conditionList;
    }

    private static Map<String, Object> fieldsToUpdate(String valuesToSet) {
        //‘active’=false, ‘cost’=10.1

        String[] split = valuesToSet
                .split(",");

        return getValues(Arrays
                .stream(split)
                .map(String::trim)
                .collect(Collectors.toList()));
    }
}
