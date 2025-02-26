package com.iti.java.foodplannerbykhalidamr;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.iti.java.foodplannerbykhalidamr.home.model.Meal;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;


@Dao
public interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertFavorite(Meal meal);

    @Delete
    Completable deleteFavorite(Meal meal);

    @Query("SELECT EXISTS(SELECT 1 FROM meals WHERE idMeal = :mealId LIMIT 1)")
    Maybe<Boolean> isFavorite(String mealId);

}