package com.iti.java.foodplannerbykhalidamr.favorites.model;

import android.util.Log;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.iti.java.foodplannerbykhalidamr.home.model.Meal;
import com.iti.java.foodplannerbykhalidamr.home.model.MealsRemoteDataSource;
import com.iti.java.foodplannerbykhalidamr.home.model.ApiService;

import java.util.HashMap;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FirestoreSyncHelper {
    private final FirebaseFirestore firestore;
    private final FavoriteDao favoriteDao;

    public FirestoreSyncHelper(FavoriteDao favoriteDao) {
        this.firestore = FirebaseFirestore.getInstance();
        this.favoriteDao = favoriteDao;
    }

    public void syncFavoritesFromFirestore(String userId) {
        firestore.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<String> favoriteMealIds = (List<String>) documentSnapshot.get("favorites");
                        if (favoriteMealIds != null) {
                            fetchAndSaveFavoritesToRoom(favoriteMealIds);
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Failed to fetch favorites", e);
                });
    }

    private void fetchAndSaveFavoritesToRoom(List<String> mealIds) {
        ApiService apiService = MealsRemoteDataSource.getApiService();
        for (String mealId : mealIds) {
            apiService.getMealById(mealId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        if (response.getMeals() != null && !response.getMeals().isEmpty()) {
                            Meal meal = response.getMeals().get(0);
                            favoriteDao.insertFavorite(meal).subscribe();
                        }
                    }, throwable -> {
                        Log.e("API", "Failed to fetch meal details", throwable);
                    });
        }
    }

    public void addFavoriteToFirestore(String userId, String mealId) {
        firestore.collection("users").document(userId)
                .set(
                        new HashMap<String, Object>() {{
                            put("favorites", FieldValue.arrayUnion(mealId));
                        }},
                        SetOptions.merge()
                )
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firestore", "Favorite added");
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Failed to add favorite", e);
                });
    }

    public void removeFavoriteFromFirestore(String userId, String mealId) {
        firestore.collection("users").document(userId)
                .update("favorites", FieldValue.arrayRemove(mealId))
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firestore", "Favorite removed");
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Failed to remove favorite", e);
                });
    }
}