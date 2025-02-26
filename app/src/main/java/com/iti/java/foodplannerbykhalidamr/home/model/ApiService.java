package com.iti.java.foodplannerbykhalidamr.home.model;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("search.php")
    Single<MealResponse> getAllMeals(@Query("f") String searchLetter);

    @GET("random.php")
    Single<MealResponse> getRandomMeal();

    @GET("lookup.php")
    Single<MealResponse> getMealById(@Query("i") String mealId);
}
