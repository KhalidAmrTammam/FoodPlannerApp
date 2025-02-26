package com.iti.java.foodplannerbykhalidamr.home.view;

import android.view.Menu;
import android.view.MenuInflater;

import androidx.annotation.NonNull;

import com.iti.java.foodplannerbykhalidamr.home.model.Meal;

import java.util.List;

public interface HomeView {
    void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater);


    void displayMealOfTheDay(Meal meal);
    void displayMeals(List<Meal> meals);
    void showError(String message);
    void logoutUser();
}
