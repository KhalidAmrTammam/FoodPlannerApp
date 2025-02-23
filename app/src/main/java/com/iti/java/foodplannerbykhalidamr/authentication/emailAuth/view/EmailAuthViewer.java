package com.iti.java.foodplannerbykhalidamr.authentication.emailAuth.view;


public interface EmailAuthViewer {
    void showSuccess(String message);
    void showError(String message);
    void navigateToHome();
}