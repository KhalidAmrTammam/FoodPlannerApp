package com.iti.java.foodplannerbykhalidamr.mealInfo.presenter;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.iti.java.foodplannerbykhalidamr.mealInfo.view.ItemInfoView;
import com.iti.java.foodplannerbykhalidamr.weeklyPlanner.model.WeeklyPlan;
import com.iti.java.foodplannerbykhalidamr.weeklyPlanner.model.WeeklyPlanDao;
import com.iti.java.foodplannerbykhalidamr.favorites.model.FavoriteDao;
import com.iti.java.foodplannerbykhalidamr.favorites.model.FirestoreSyncHelper;
import com.iti.java.foodplannerbykhalidamr.home.model.ApiService;
import com.iti.java.foodplannerbykhalidamr.home.model.Meal;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ItemInfoPresenter implements ItemInfoPresenterInterface {
    private final ApiService apiService;
    private final FavoriteDao favoriteDao;
    private ItemInfoView view;
    String youtubeUrl;
    private final FirestoreSyncHelper firestoreSyncHelper;
    private Meal currentMeal;
    private final WeeklyPlanDao weeklyPlanDao;



    public ItemInfoPresenter(ApiService apiService, FavoriteDao favoriteDao, WeeklyPlanDao weeklyPlanDao) {
        this.apiService = apiService;
        this.favoriteDao = favoriteDao;
        this.firestoreSyncHelper = new FirestoreSyncHelper(favoriteDao);
        this.weeklyPlanDao = weeklyPlanDao;

    }
    public void setCurrentMeal(Meal meal) {
        this.currentMeal = meal;
    }

    public Meal getCurrentMeal() {
        return currentMeal;
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
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        favoriteDao.isFavorite(meal.getIdMeal())
                .flatMapCompletable(isFavorite -> {
                    if (isFavorite) {
                        firestoreSyncHelper.removeFavoriteFromFirestore(userId, meal.getIdMeal());
                        return favoriteDao.deleteFavorite(meal);
                    } else {
                        firestoreSyncHelper.addFavoriteToFirestore(userId, meal.getIdMeal());
                        return favoriteDao.insertFavorite(meal);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            favoriteDao.isFavorite(meal.getIdMeal())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            isFavorite -> view.updateFavoriteStatus(isFavorite),
                                            throwable -> view.showError("Failed to check favorite status")
                                    );
                        },
                        throwable -> view.showError("Failed to update favorite status")
                );
    }

    public void checkFavoriteStatus(String mealId) {
        favoriteDao.isFavorite(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        isFavorite -> view.updateFavoriteStatus(isFavorite),
                        throwable -> view.showError("Failed to check favorite status")
                );
    }

    @Override
    public void saveToWeeklyPlan(String date) {
        Log.d("SaveToPlan", "saveToWeeklyPlan CALLED for date: " + date);
        if (currentMeal == null) return;

        weeklyPlanDao.mealExists(date, currentMeal.getIdMeal())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(count -> {
                    if (count > 0) {
                        view.showMessage("Meal already exists for this day!");
                    } else {
                        WeeklyPlan plan = new WeeklyPlan();
                        plan.setDate(date);
                        plan.setMealId(currentMeal.getIdMeal());
                        plan.setMeal(currentMeal);

                        weeklyPlanDao.insertPlan(plan)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                            Log.d("SaveToPlan", "Insertion successful");
                                            view.showMessage("Added to weekly plan!");
                                        },
                                        e -> Log.e("WeeklyPlan", "Error saving plan", e));
                    }
                }, throwable -> view.showError("Error checking meal existence"));
    }


}


