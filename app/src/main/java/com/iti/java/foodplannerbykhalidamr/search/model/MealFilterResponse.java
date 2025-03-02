package com.iti.java.foodplannerbykhalidamr.search.model;

import com.iti.java.foodplannerbykhalidamr.home.model.Meal;

import java.util.List;

public class MealFilterResponse {
    private List<Meal> meals;

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
