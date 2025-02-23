package com.iti.java.foodplannerbykhalidamr.authentication.emailAuth.model;


import com.google.firebase.auth.FirebaseUser;

public interface EmailAuthCompleteListener {
    void onSuccess(FirebaseUser user);
    void onFailure(String errorMessage);
}