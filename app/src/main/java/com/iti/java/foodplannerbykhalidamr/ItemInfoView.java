package com.iti.java.foodplannerbykhalidamr;

import com.iti.java.foodplannerbykhalidamr.home.model.Meal;
import java.util.List;
import java.util.Map;

public interface ItemInfoView {
    void showMealDetails(Meal meal);
    void showIngredients(Meal meal);
    void showError(String message);
    void loadYouTubeVideo(String embedUrl);
    void updateFavoriteStatus(boolean isFavorite);

}