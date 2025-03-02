package com.iti.java.foodplannerbykhalidamr;
import com.iti.java.foodplannerbykhalidamr.home.model.ApiService;
import com.iti.java.foodplannerbykhalidamr.home.model.Meal;
import com.iti.java.foodplannerbykhalidamr.home.model.MealsRemoteDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenter {

    private final SearchViewInterface view;
    private final ApiService apiService;
    private List<Meal> mealList = new ArrayList<>();

    public SearchPresenter(SearchViewInterface view) {
        this.view = view;
        this.apiService = MealsRemoteDataSource.getApiService();
    }

    public void fetchCategories() {
        apiService.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> view.showCategories(response.getCategories()),
                        throwable -> view.showError("Failed to load categories"));
    }

    public void fetchIngredients() {
        apiService.getIngredients()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> view.showIngredients(response.getMeals()),
                        throwable -> view.showError("Failed to load ingredients"));
    }

    public void fetchCountries() {
        apiService.getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> view.showCountries(response.getMeals()),
                        throwable -> view.showError("Failed to load countries"+throwable.getLocalizedMessage()));
    }

    public void fetchMealsByCategory(String category) {
        apiService.getMealsByCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            List<Meal> meals = response.getMeals() != null ? response.getMeals() : new ArrayList<>();
                            view.showSearchResults(meals);
                        },
                        throwable -> view.showError("Failed to search meals by category"));
    }

    public void fetchMealsByIngredient(String ingredient) {
        apiService.getMealsByIngredient(ingredient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            List<Meal> meals = response.getMeals() != null ? response.getMeals() : new ArrayList<>();
                            view.showSearchResults(meals);
                        },
                        throwable -> view.showError("Failed to search meals by ingredient"+throwable.getLocalizedMessage()));
    }


    public void fetchMealsByCountry(String country) {
        apiService.getMealsByCountry(country)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            List<Meal> meals = response.getMeals() != null ? response.getMeals() : new ArrayList<>();
                            view.showSearchResults(meals);
                        },
                        throwable -> view.showError("Failed to search meals by country"+throwable.getLocalizedMessage()));
    }

    public void searchMealsByName(String name) {
        apiService.searchMealsByName(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    List<Meal> meals = response.getMeals() != null ? response.getMeals() : new ArrayList<>();
                    view.showSearchResults(meals);
                }, throwable -> view.showError("Failed to search meals by name"));
    }
    public List<String> filterList(List<String> source, String query) {
        List<String> filtered = new ArrayList<>();
        if (query.isEmpty()) {
            filtered.addAll(source);
        } else {
            String lowerQuery = query.toLowerCase();
            for (String item : source) {
                if (item.toLowerCase().contains(lowerQuery)) {
                    filtered.add(item);
                }
            }
        }
        return filtered;
    }
}