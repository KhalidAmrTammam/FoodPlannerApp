package com.iti.java.foodplannerbykhalidamr.authentication.googleAuth.presenter;

import android.util.Log;
import com.google.firebase.auth.FirebaseUser;
import com.iti.java.foodplannerbykhalidamr.authentication.AuthNavigatorInterface;
import com.iti.java.foodplannerbykhalidamr.authentication.googleAuth.model.GoogleAuthModelInterface;

public class GoogleAuthPresenter /*implements GoogleAuthPresenterInterface */{

    private final GoogleAuthModelInterface model;
    private final AuthNavigatorInterface navigator;

    public GoogleAuthPresenter(GoogleAuthModelInterface model, AuthNavigatorInterface navigator) {
        this.model = model;
        this.navigator = navigator;
    }

    //@Override
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

    //@Override
    public void navigateToEmailLogin() {
        navigator.goToEmailLogin();
    }

    //@Override
    public void navigateToHome() {
        navigator.goToHome();
    }

    //@Override
    public void navigateToSignUp() {
        navigator.goToSignUp();
    }
}
