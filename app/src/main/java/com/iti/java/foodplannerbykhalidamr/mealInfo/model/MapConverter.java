package com.iti.java.foodplannerbykhalidamr.mealInfo.model;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Map;

public class MapConverter {
    private static final Gson gson = new Gson();
    private static final Type mapType = new TypeToken<Map<String, String>>() {}.getType();

    @TypeConverter
    public static String fromMap(Map<String, String> map) {
        return gson.toJson(map);
    }

    @TypeConverter
    public static Map<String, String> toMap(String json) {
        return gson.fromJson(json, mapType);
    }
}