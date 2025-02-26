package com.iti.java.foodplannerbykhalidamr;

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
        meal.setIdMeal(jsonObject.get("idMeal").getAsString());
        meal.setStrMeal(jsonObject.get("strMeal").getAsString());
        meal.setStrMealThumb(jsonObject.get("strMealThumb").getAsString());
        meal.setStrArea(jsonObject.get("strArea").getAsString());
        meal.setStrInstructions(jsonObject.get("strInstructions").getAsString());
        meal.setStrYoutube(jsonObject.get("strYoutube").getAsString());


        Map<String, String> ingredients = new LinkedHashMap<>();
        Map<String, String> measures = new LinkedHashMap<>();

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

        meal.setStrIngredients(ingredients);
        meal.setStrMeasure(measures);
        return meal;
    }

    private String getStringOrEmpty(JsonObject obj, String key) {
        JsonElement element = obj.get(key);
        return element.isJsonNull() ? "" : element.getAsString().trim();
    }
}