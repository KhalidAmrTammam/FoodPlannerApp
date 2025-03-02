package com.iti.java.foodplannerbykhalidamr.favorites;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.iti.java.foodplannerbykhalidamr.weeklyPlanner.model.WeeklyPlan;
import com.iti.java.foodplannerbykhalidamr.weeklyPlanner.model.WeeklyPlanDao;
import com.iti.java.foodplannerbykhalidamr.home.model.Meal;

@Database(entities = {Meal.class, WeeklyPlan.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "food_planner_db"
                    ).fallbackToDestructiveMigration()
                     .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract FavoriteDao favoriteDao();
    public abstract WeeklyPlanDao weeklyPlanDao();
}