package com.iti.java.foodplannerbykhalidamr.authentication.googleAuth.presenter;

import android.util.Log;
import com.google.firebase.auth.FirebaseUser;
import com.iti.java.foodplannerbykhalidamr.authentication.AuthNavigatorInterface;
import com.iti.java.foodplannerbykhalidamr.authentication.googleAuth.model.GoogleAuthModelInterface;

public class GoogleAuthPresenter {

    private final GoogleAuthModelInterface model;
    private final AuthNavigatorInterface navigator;

    public GoogleAuthPresenter(GoogleAuthModelInterface model, AuthNavigatorInterface navigator) {
        this.model = model;
        this.navigator = navigator;
    }

    public void handleGoogleSignIn(String idToken) {
        model.signInWithGoogle(idToken, new GoogleAuthModelInterface.OnAuthCompleteListener() {
            @Override
            public void onSuccess(FirebaseUser user) {
                navigator.goToHome();
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
