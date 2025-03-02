package com.iti.java.foodplannerbykhalidamr.authentication;


import android.content.Context;

public interface AuthNavigatorInterface {
    void goToHome();
    void goToEmailLogin();
    void goToSignUp();

    Context getContext();
}
