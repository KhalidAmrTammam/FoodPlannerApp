package com.iti.java.foodplannerbykhalidamr.splahScreen.presenter;

import android.os.Handler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iti.java.foodplannerbykhalidamr.splahScreen.view.SplashViewer;

public class SplashPresenter {
    private final SplashViewer view;

    public SplashPresenter(SplashViewer view) {
        this.view = view;
    }

    public void checkUserSession() {
        new Handler().postDelayed(() -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                view.navigateToHome();
            } else {
                view.navigateToAuthentication();
            }
        }, 3000); // Splash delay
    }
}
