package com.iti.java.foodplannerbykhalidamr.authentication.emailAuth.model;

import com.google.firebase.auth.FirebaseAuth;


public class EmailAuth implements EmailAuthenticator {
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    public void signUp(String email, String password, EmailAuthCompleteListener listener) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onSuccess(auth.getCurrentUser());
                    } else {
                        listener.onFailure(task.getException().getMessage());
                    }
                });
    }

    @Override
    public void login(String email, String password, EmailAuthCompleteListener listener) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onSuccess(auth.getCurrentUser());
                    } else {
                        listener.onFailure(task.getException().getMessage());
                    }
                });
    }
}
