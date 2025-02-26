package com.iti.java.foodplannerbykhalidamr;

import android.util.Log;
import com.iti.java.foodplannerbykhalidamr.home.model.ApiService;
import com.iti.java.foodplannerbykhalidamr.home.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ItemInfoPresenter implements ItemInfoPresenterInterface {
    private final ApiService apiService;
    private final FavoriteDao favoriteDao;
    private ItemInfoView view;
    String youtubeUrl;

    public ItemInfoPresenter(ApiService apiService, FavoriteDao favoriteDao) {
        this.apiService = apiService;
        this.favoriteDao = favoriteDao;
    }


    public void attachView(ItemInfoView view) {
        this.view = view;
    }

    @Override
    public void loadMealDetails(String mealId) {
        apiService.getMealById(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            if (response.getMeals() != null && !response.getMeals().isEmpty()) {
                                Meal meal = response.getMeals().get(0);
                                view.showMealDetails(meal);
                                view.showIngredients(meal);
                                youtubeUrl = meal.getStrYoutube();
                                if (youtubeUrl != null && !youtubeUrl.isEmpty()) {
                                    String embedUrl = getYouTubeEmbedUrl(youtubeUrl);
                                    view.loadYouTubeVideo(embedUrl);
                                }
                            }
                        },
                        throwable -> view.showError(throwable.getMessage())
                );

    }
    private String getYouTubeEmbedUrl(String youtubeUrl) {
        String videoId = extractYouTubeVideoId(youtubeUrl);
        if (videoId != null) {
            return "https://www.youtube.com/embed/" + videoId;
        }
        return null;
    }
    private String extractYouTubeVideoId(String youtubeUrl) {
        String videoId = null;
        if (youtubeUrl != null && youtubeUrl.trim().length() > 0) {
            String[] split = youtubeUrl.split("v=");
            if (split.length > 1) {
                videoId = split[1];
                int ampersandIndex = videoId.indexOf('&');
                if (ampersandIndex != -1) {
                    videoId = videoId.substring(0, ampersandIndex);
                }
            }
        }
        return videoId;
    }

    @Override
    public void toggleFavorite(Meal meal) {
        favoriteDao.isFavorite(meal.getIdMeal())
                .flatMapCompletable(isFavorite ->
                        isFavorite ? favoriteDao.deleteFavorite(meal) : favoriteDao.insertFavorite(meal)
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                        () -> checkFavoriteStatus(meal.getIdMeal()),
                        throwable -> view.showError("Favorite update failed")
                );
    }


    public void checkFavoriteStatus(String mealId) {
        favoriteDao.isFavorite(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        isFavorite -> view.updateFavoriteStatus(isFavorite),
                        throwable -> view.showError("Favorite check failed")
                );
    }




}


