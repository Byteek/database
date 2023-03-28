package com.lessons.home.database;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Types {
    public Map<String, Type> USER;

    public Types(){
        Hashtable<String, Type> stringObjectHashMap = new Hashtable<>();
        stringObjectHashMap.put("id", new Lng());
        stringObjectHashMap.put("lastName", new Str());
        stringObjectHashMap.put("cost",  new Dbl());
        stringObjectHashMap.put("age", new Lng());
        stringObjectHashMap.put( "active", new Bool());
        this.USER = stringObjectHashMap;
    }

}
