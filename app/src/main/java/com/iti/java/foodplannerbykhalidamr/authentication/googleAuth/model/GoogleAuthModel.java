package com.iti.java.foodplannerbykhalidamr.authentication.googleAuth.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class GoogleAuthModel implements GoogleAuthModelInterface {
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    public void signInWithGoogle(String idToken, OnAuthCompleteListener listener) {
        auth.signInWithCredential(GoogleAuthProvider.getCredential(idToken, null))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onSuccess(auth.getCurrentUser());
                    } else {
                        listener.onFailure(task.getException().getMessage());
                    }
                });
    }
}
