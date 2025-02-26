/*
package com.iti.java.foodplannerbykhalidamr.home.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import io.reactivex.rxjava3.annotations.NonNull;

@Entity(tableName = "ingredients",
foreignKeys = @ForeignKey(entity = Meal.class,
        parentColumns = "idMeal",
        childColumns = "mealId",
        onDelete = CASCADE),
indices = {@Index("mealId")})
public class Ingredient {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private String mealId;
    private String ingredient;
    private String measure;

    public Ingredient(String mealId, String ingredient, String measure) {
        this.mealId = mealId;
        this.ingredient = ingredient;
        this.measure = measure;
    }
    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    @NonNull
    public int getId() {        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }
}
*/
