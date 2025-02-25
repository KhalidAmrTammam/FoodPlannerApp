package com.iti.java.foodplannerbykhalidamr.home.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Meal {
    @SerializedName("idMeal")
    private String idMeal;

    @SerializedName("strMeal")
    private String strMeal;

    @SerializedName("strMealThumb")
    private String strMealThumb;

    @SerializedName("strInstructions")
    private String strInstructions;

    @SerializedName("strYoutube")
    private String strYoutube;

    @SerializedName("strArea")
    private String strArea;

    private List<Ingredient> ingredients;

    public Meal(String idMeal, String strMeal, String strMealThumb, String strInstructions, String strYoutube, String strArea, List<Ingredient> ingredients) {
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
        this.strInstructions = strInstructions;
        this.strYoutube = strYoutube;
        this.strArea = strArea;
        this.ingredients = ingredients;
    }

    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }


    public static class Ingredient {
        private String name;
        private String measure;
        private String imageUrl;

        public Ingredient(String name, String measure) {
            this.name = name;
            this.measure = measure;
            this.imageUrl = "https://www.themealdb.com/images/ingredients/" + name + ".png";
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMeasure() {
            return measure;
        }

        public void setMeasure(String measure) {
            this.measure = measure;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}