package com.iti.java.foodplannerbykhalidamr.authentication.emailAuth.presenter;


import com.google.firebase.auth.FirebaseUser;
import com.iti.java.foodplannerbykhalidamr.authentication.emailAuth.model.EmailAuthCompleteListener;
import com.iti.java.foodplannerbykhalidamr.authentication.emailAuth.view.EmailAuthViewer;
import com.iti.java.foodplannerbykhalidamr.authentication.emailAuth.model.EmailAuthenticator;
import com.iti.java.foodplannerbykhalidamr.favorites.model.FirestoreSyncHelper;

public class EmailAuthPresenter {
    private final EmailAuthenticator model;
    private final EmailAuthViewer view;
    private final FirestoreSyncHelper firestoreSyncHelper;


    public EmailAuthPresenter(EmailAuthenticator model, EmailAuthViewer view, FirestoreSyncHelper firestoreSyncHelper) {
        this.model = model;
        this.view = view;
        this.firestoreSyncHelper = firestoreSyncHelper;
    }

    public void signUp(String email, String password) {
        model.signUp(email, password, new EmailAuthCompleteListener() {
            @Override
            public void onSuccess(FirebaseUser user) {
                view.showSuccess("Signup successful!");
                view.navigateToHome();
              // firestoreSyncHelper.syncFavoritesFromFirestore(user.getUid());

            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError(errorMessage);
            }
        });
    }

    public void login(String email, String password) {
        model.login(email, password, new EmailAuthCompleteListener() {
            @Override
            public void onSuccess(FirebaseUser user) {

                view.showSuccess("Login successful!");
                view.navigateToHome();
              //  firestoreSyncHelper.syncFavoritesFromFirestore(user.getUid());

            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError(errorMessage);
            }
        });
    }
    public void logout() {
        model.logout();
        view.showSuccess("Logged out successfully");
    }
}
