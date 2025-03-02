package com.iti.java.foodplannerbykhalidamr.favorites;

import com.iti.java.foodplannerbykhalidamr.home.model.Meal;
import java.util.List;

public interface FavoritesView {
    void showFavorites(List<Meal> favorites);
    void onFavoriteDeleted(Meal meal);
    void showError(String message);
}