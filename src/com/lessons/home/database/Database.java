package com.lessons.home.database;

import com.lessons.home.database.types.interfaces.Type;
import com.lessons.home.database.types.Tables;

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
        List<String> insertValues = Arrays
                .stream(command.replace("INSERT VALUES", "")
                        .trim()
                        .split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        Map<String, Object> object = new HashMap<>();

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
                object.put(key, value);
            }
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
}
