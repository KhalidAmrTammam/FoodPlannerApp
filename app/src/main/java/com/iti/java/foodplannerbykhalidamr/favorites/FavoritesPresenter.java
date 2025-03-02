package com.iti.java.foodplannerbykhalidamr.favorites;

import com.iti.java.foodplannerbykhalidamr.home.model.Meal;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;



public class FavoritesPresenter {
    private final FavoriteDao favoriteDao;
    private final FavoritesView view;

    public FavoritesPresenter(FavoriteDao favoriteDao, FavoritesView view) {
        this.favoriteDao = favoriteDao;
        this.view = view;
    }

    public void loadFavorites() {
        favoriteDao.getAllFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        favorites -> view.showFavorites(favorites),
                        throwable -> view.showError("Failed to load favorites")
                );
    }

    public void deleteFavorite(Meal meal) {
        favoriteDao.deleteFavorite(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> view.onFavoriteDeleted(meal),
                        throwable -> view.showError("Failed to delete favorite")
                );
    }
}