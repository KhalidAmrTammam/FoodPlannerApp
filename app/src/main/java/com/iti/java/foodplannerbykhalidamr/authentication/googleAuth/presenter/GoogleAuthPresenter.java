package com.iti.java.foodplannerbykhalidamr.authentication.googleAuth.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.iti.java.foodplannerbykhalidamr.authentication.AuthNavigatorInterface;
import com.iti.java.foodplannerbykhalidamr.authentication.googleAuth.model.GoogleAuthModelInterface;
import com.iti.java.foodplannerbykhalidamr.favorites.FirestoreSyncHelper;

public class GoogleAuthPresenter {

    private final GoogleAuthModelInterface model;
    private final AuthNavigatorInterface navigator;
    private final FirestoreSyncHelper firestoreSyncHelper;

    public GoogleAuthPresenter(GoogleAuthModelInterface model, AuthNavigatorInterface navigator, FirestoreSyncHelper firestoreSyncHelper) {
        this.model = model;
        this.navigator = navigator;
        this.firestoreSyncHelper = firestoreSyncHelper;
    }

    public void handleGoogleSignIn(String idToken) {
        model.signInWithGoogle(idToken, new GoogleAuthModelInterface.OnAuthCompleteListener() {
            @Override
            public void onSuccess(FirebaseUser user) {
                Log.i("TAG", "onSuccess: ");
                navigator.getContext().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
                        .edit()
                        .putBoolean("isGuest", false)
                        .apply();

                navigator.goToHome();
                //firestoreSyncHelper.syncFavoritesFromFirestore(user.getUid());


            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("AuthError", errorMessage);
            }
        });
    }

    public void navigateToEmailLogin() {
        navigator.goToEmailLogin();
    }

    public void navigateToHome() {
        navigator.goToHome();
    }

    public void navigateToSignUp() {
        navigator.goToSignUp();
    }
}
