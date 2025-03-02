package com.iti.java.foodplannerbykhalidamr.weeklyPlanner.presenter;

import android.util.Log;

import com.iti.java.foodplannerbykhalidamr.weeklyPlanner.model.WeeklyPlan;
import com.iti.java.foodplannerbykhalidamr.weeklyPlanner.model.WeeklyPlanDao;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class WeeklyPlannerPresenter {
    private final WeeklyPlanDao weeklyPlanDao;
    private final PublishSubject<List<WeeklyPlan>> plansSubject = PublishSubject.create();

    public WeeklyPlannerPresenter(WeeklyPlanDao weeklyPlanDao) {
        this.weeklyPlanDao = weeklyPlanDao;
    }

    public void loadPlansForDate(String date) {
        weeklyPlanDao.getPlansForDate(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response ->plansSubject.onNext(response),
                        throwable -> plansSubject.onError(throwable)
                );
    }
    public void deletePlan(WeeklyPlan plan) {
        weeklyPlanDao.deletePlan(plan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            Log.d("DeletePlan", "Meal deleted: " + plan.getMeal().getStrMeal());
                            loadPlansForDate(plan.getDate());
                        },
                        throwable -> Log.e("DeletePlan", "Failed to delete: " + throwable.getMessage())
                );
    }


    public Observable<List<WeeklyPlan>> getPlansObservable() {
        return plansSubject.hide();
    }

}