package com.iti.java.foodplannerbykhalidamr;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;

@Dao
public interface WeeklyPlanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertPlan(WeeklyPlan plan);

    @Query("SELECT * FROM weekly_plans WHERE date = :date")
    Flowable<List<WeeklyPlan>> getPlansForDate(String date);

    @Delete
    Completable deletePlan(WeeklyPlan plan);

    @Query("SELECT COUNT(*) FROM weekly_plans WHERE date = :date AND meal_id = :mealId")
    Maybe<Integer> mealExists(String date, String mealId);
}