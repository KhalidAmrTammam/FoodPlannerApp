package com.iti.java.foodplannerbykhalidamr.home.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Locale;

public class DailyMealManager {
    private static final String PREF_NAME = "DailyMealPrefs";
    private static final String KEY_MEAL_ID = "daily_meal_id";
    private static final String KEY_LAST_DATE = "last_date";

    public static void saveDailyMeal(Context context, String mealId) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String currentDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault())
                .format(Calendar.getInstance().getTime());
        prefs.edit()
                .putString(KEY_MEAL_ID, mealId)
                .putString(KEY_LAST_DATE, currentDate)
                .apply();
    }

    public static String getDailyMealId(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                .getString(KEY_MEAL_ID, null);
    }

    public static boolean isNewDay(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String lastDate = prefs.getString(KEY_LAST_DATE, "");
        String currentDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault())
                .format(Calendar.getInstance().getTime());
        return !currentDate.equals(lastDate);
    }
}