package com.iti.java.foodplannerbykhalidamr.home.presenter;

import android.content.Context;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.iti.java.foodplannerbykhalidamr.authentication.emailAuth.presenter.EmailAuthPresenter;
import com.iti.java.foodplannerbykhalidamr.home.model.DailyMealManager;
import com.iti.java.foodplannerbykhalidamr.home.view.HomeView;
import com.iti.java.foodplannerbykhalidamr.home.model.ApiService;
import com.iti.java.foodplannerbykhalidamr.home.model.Meal;

import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenter {
    private final HomeView homeView;
    private final FirebaseAuth auth;
    private ApiService apiService;
    private Context context;
    private EmailAuthPresenter presenter;

    public HomePresenter(HomeView homeView,  FirebaseAuth auth1, ApiService apiService, Context context) {
        this.homeView = homeView;
        this.auth = auth1;
        this.apiService = apiService;
        this.context = context;
    }


    public void logout() {
        auth.signOut();
        presenter.logout();
    }



    public void loadMealOfTheDay() {
        if (DailyMealManager.isNewDay(context)) {
            fetchRandomMeal();
        } else {
            String cachedMealId = DailyMealManager.getDailyMealId(context);
            if (cachedMealId != null) fetchMealById(cachedMealId);
            else fetchRandomMeal();
        }
    }

    private void fetchMealById(String mealId) {
        apiService.getMealById(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.getMeals() != null && !response.getMeals().isEmpty()) {
                        homeView.displayMealOfTheDay(response.getMeals().get(0));
                    } else {
                        fetchRandomMeal();
                    }
                }, throwable -> {
                    homeView.showError("Failed to load saved meal");
                    fetchRandomMeal();
                });
    }
    private void fetchRandomMeal() {
        apiService.getRandomMeal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (homeView != null && response.getMeals() != null && !response.getMeals().isEmpty()) {
                        Meal meal = response.getMeals().get(0);
                        DailyMealManager.saveDailyMeal(context, meal.getIdMeal());
                        homeView.displayMealOfTheDay(meal);
                    }
                }, throwable -> {
                    if (homeView != null) homeView.showError("Failed to load meal of the day");
                });
    }

    public void loadMeals() {
        apiService.getAllMeals("p")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    List<Meal> meals = response.getMeals();
                    if (meals != null) {
                        homeView.displayMeals(meals);
                    }
                }, throwable -> {
                    homeView.showError("Failed to load meals");
                    Log.i("TAG", "loadMeals: " + throwable.getMessage());
                });
    }
}


