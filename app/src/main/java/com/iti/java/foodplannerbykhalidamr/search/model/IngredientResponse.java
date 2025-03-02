package com.iti.java.foodplannerbykhalidamr.search.model;

import java.util.List;

public class IngredientResponse {
    private List<Ingredient> meals;

    public List<Ingredient> getMeals() {
        return meals;
    }

    public void setMeals(List<Ingredient> meals) {
        this.meals = meals;
    }
}