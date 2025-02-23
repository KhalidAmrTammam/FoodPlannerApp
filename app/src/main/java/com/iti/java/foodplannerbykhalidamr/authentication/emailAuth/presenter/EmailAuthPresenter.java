package com.iti.java.foodplannerbykhalidamr.authentication.emailAuth.presenter;


import com.google.firebase.auth.FirebaseUser;
import com.iti.java.foodplannerbykhalidamr.authentication.emailAuth.model.EmailAuthCompleteListener;
//import com.iti.java.foodplannerbykhalidamr.authentication.emailAuth.EmailAuthPresenterInterface;
import com.iti.java.foodplannerbykhalidamr.authentication.emailAuth.view.EmailAuthViewer;
import com.iti.java.foodplannerbykhalidamr.authentication.emailAuth.model.EmailAuthenticator;

public class EmailAuthPresenter /*implements EmailAuthPresenterInterface*/ {
    private final EmailAuthenticator model;
    private final EmailAuthViewer view;

    public EmailAuthPresenter(EmailAuthenticator model, EmailAuthViewer view) {
        this.model = model;
        this.view = view;
    }

   // @Override
    public void signUp(String email, String password) {
        model.signUp(email, password, new EmailAuthCompleteListener() {
            @Override
            public void onSuccess(FirebaseUser user) {
                view.showSuccess("Signup successful!");
                view.navigateToHome();
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError(errorMessage);
            }
        });
    }

    //@Override
    public void login(String email, String password) {
        model.login(email, password, new EmailAuthCompleteListener() {
            @Override
            public void onSuccess(FirebaseUser user) {
                view.showSuccess("Login successful!");
                view.navigateToHome();
            }

            @Override
            public void onFailure(String errorMessage) {
                view.showError(errorMessage);
            }
        });
    }
}
