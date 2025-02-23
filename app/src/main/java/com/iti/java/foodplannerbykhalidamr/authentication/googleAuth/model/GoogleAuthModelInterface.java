package com.iti.java.foodplannerbykhalidamr.authentication.googleAuth.model;

import com.google.firebase.auth.FirebaseUser;

public interface GoogleAuthModelInterface {
    void signInWithGoogle(String idToken, OnAuthCompleteListener listener);

    interface OnAuthCompleteListener {
        void onSuccess(FirebaseUser user);
        void onFailure(String errorMessage);
    }
}