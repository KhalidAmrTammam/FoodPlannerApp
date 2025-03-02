package com.iti.java.foodplannerbykhalidamr.weeklyPlanner.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.iti.java.foodplannerbykhalidamr.home.model.Meal;

@Entity(tableName = "weekly_plans",primaryKeys = {"date", "meal_id"} )
public class WeeklyPlan {

    @NonNull
    @ColumnInfo(name = "date")
    private String date; // Format: "yyyy-MM-dd"
    @NonNull
    @ColumnInfo(name = "meal_id")
    private String mealId;

    @Embedded
    private Meal meal;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }
}