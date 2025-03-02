package com.iti.java.foodplannerbykhalidamr.home.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.iti.java.foodplannerbykhalidamr.mealInfo.model.MapConverter;

import java.util.Map;


@Entity(tableName = "meals")
@TypeConverters(MapConverter.class)
public class Meal {
    @PrimaryKey
    @NonNull
    private String idMeal;
    private String strMeal;
    private String strMealThumb;
    private String strInstructions;
    private String strYoutube;
    private String strArea;
    private Map<String, String> strIngredients;
    private Map<String, String> strMeasure;

    @NonNull
    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(@NonNull String idMeal) {
        this.idMeal = idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }

    public String getStrYoutube() {
        return strYoutube;
    }

    public void setStrYoutube(String strYoutube) {
        this.strYoutube = strYoutube;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    public Map<String, String> getStrIngredients() {
        return strIngredients;
    }

    public void setStrIngredients(Map<String, String> strIngredients) {
        this.strIngredients = strIngredients;
    }

    public Map<String, String> getStrMeasure() {
        return strMeasure;
    }

    public void setStrMeasure(Map<String, String> strMeasure) {
        this.strMeasure = strMeasure;
    }
}