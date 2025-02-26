package com.iti.java.foodplannerbykhalidamr;


import com.iti.java.foodplannerbykhalidamr.home.model.Meal;

public interface ItemInfoPresenterInterface {
    void loadMealDetails(String mealId);
    void toggleFavorite(Meal meal);
    void checkFavoriteStatus(String mealId);

}