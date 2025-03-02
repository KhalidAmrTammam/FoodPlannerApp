package com.iti.java.foodplannerbykhalidamr.authentication.emailAuth.model;


public interface EmailAuthenticator {
    void signUp(String email, String password, EmailAuthCompleteListener listener);
    void login(String email, String password, EmailAuthCompleteListener listener);
    void logout();

}