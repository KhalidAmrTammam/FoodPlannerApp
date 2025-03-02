package com.iti.java.foodplannerbykhalidamr.home.model;

import com.iti.java.foodplannerbykhalidamr.search.model.CategoryResponse;
import com.iti.java.foodplannerbykhalidamr.search.model.CountryResponse;
import com.iti.java.foodplannerbykhalidamr.search.model.IngredientResponse;
import com.iti.java.foodplannerbykhalidamr.search.model.MealFilterResponse;

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

    @GET("categories.php")
    Single<CategoryResponse> getCategories();

    @GET("list.php?i=list")
    Single<IngredientResponse> getIngredients();

    @GET("list.php?a=list")
    Single<CountryResponse> getCountries();

    @GET("filter.php")
    Single<MealFilterResponse> getMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Single<MealFilterResponse> getMealsByIngredient(@Query("i") String ingredient);

    @GET("filter.php")
    Single<MealFilterResponse> getMealsByCountry(@Query("a") String country);

    @GET("search.php")
    Single<MealResponse> searchMealsByName(@Query("s") String name);
}
