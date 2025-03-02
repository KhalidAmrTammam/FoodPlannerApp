package com.iti.java.foodplannerbykhalidamr.mealInfo;

import android.content.Context;

import com.iti.java.foodplannerbykhalidamr.home.model.Meal;

public interface ItemInfoView {
    Context getViewContext();

    void showMealDetails(Meal meal);
    void showIngredients(Meal meal);
    void showError(String message);
    void loadYouTubeVideo(String embedUrl);
    void updateFavoriteStatus(boolean isFavorite);
    void showMessage(String message);

}