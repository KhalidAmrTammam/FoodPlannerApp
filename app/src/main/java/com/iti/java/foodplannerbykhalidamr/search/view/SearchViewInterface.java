package com.iti.java.foodplannerbykhalidamr.search.view;

import com.iti.java.foodplannerbykhalidamr.home.model.Meal;
import com.iti.java.foodplannerbykhalidamr.search.model.Category;
import com.iti.java.foodplannerbykhalidamr.search.model.Country;
import com.iti.java.foodplannerbykhalidamr.search.model.Ingredient;

import java.util.List;

public interface SearchViewInterface {
    void showSearchResults(List<Meal> meals);
    void navigateToMealDetails(Meal meal);
    void showCategories(List<Category> categories);
    void showIngredients(List<Ingredient> ingredients);
    void showCountries(List<Country> countries);
    void showError(String message);
}