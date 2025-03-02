package com.iti.java.foodplannerbykhalidamr;

import com.iti.java.foodplannerbykhalidamr.home.model.Meal;
import java.util.List;

public interface SearchViewInterface {
    void showSearchResults(List<Meal> meals);
    void navigateToMealDetails(Meal meal);
    void showCategories(List<Category> categories);
    void showIngredients(List<Ingredient> ingredients);
    void showCountries(List<Country> countries);
    void showError(String message);
}