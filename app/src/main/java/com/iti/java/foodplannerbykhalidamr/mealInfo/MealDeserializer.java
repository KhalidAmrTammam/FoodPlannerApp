package com.iti.java.foodplannerbykhalidamr.mealInfo;

import com.google.gson.*;
import com.iti.java.foodplannerbykhalidamr.home.model.Meal;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public class MealDeserializer implements JsonDeserializer<Meal> {
    @Override
    public Meal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();
        Meal meal = new Meal();
        if(jsonObject.has("idMeal"))
            meal.setIdMeal(jsonObject.get("idMeal").getAsString());
        if(jsonObject.has("strMeal"))
            meal.setStrMeal(jsonObject.get("strMeal").getAsString());
        if(jsonObject.has("strMealThumb"))
            meal.setStrMealThumb(jsonObject.get("strMealThumb").getAsString());
        if(jsonObject.has("strArea"))
            meal.setStrArea(jsonObject.get("strArea").getAsString());
        if(jsonObject.has("strInstructions"))
            meal.setStrInstructions(jsonObject.get("strInstructions").getAsString());
        if(jsonObject.has("strYoutube"))
            meal.setStrYoutube(jsonObject.get("strYoutube").getAsString());


        Map<String, String> ingredients = new LinkedHashMap<>();
        Map<String, String> measures = new LinkedHashMap<>();
        if(jsonObject.has("strIngredient1")) {
            for (int i = 1; i <= 20; i++) {
                String ingredientKey = "strIngredient" + i;
                String measureKey = "strMeasure" + i;

                String ingredient = getStringOrEmpty(jsonObject, ingredientKey);
                String measure = getStringOrEmpty(jsonObject, measureKey);

                if (!ingredient.isEmpty()) {
                    ingredients.put(String.valueOf(i), ingredient);
                    measures.put(String.valueOf(i), measure);
                }
            }
        }

        meal.setStrIngredients(ingredients);
        meal.setStrMeasure(measures);
        return meal;
    }

    private String getStringOrEmpty(JsonObject obj, String key) {
        JsonElement element = obj.get(key);
        return element.isJsonNull() ? "" : element.getAsString().trim();
    }
}