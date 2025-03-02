package com.iti.java.foodplannerbykhalidamr.authentication.emailAuth.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;
import com.iti.java.foodplannerbykhalidamr.R;
import com.iti.java.foodplannerbykhalidamr.authentication.emailAuth.model.EmailAuth;
import com.iti.java.foodplannerbykhalidamr.authentication.emailAuth.presenter.EmailAuthPresenter;
import com.iti.java.foodplannerbykhalidamr.favorites.AppDatabase;
import com.iti.java.foodplannerbykhalidamr.favorites.FavoriteDao;
import com.iti.java.foodplannerbykhalidamr.favorites.FirestoreSyncHelper;
//import com.iti.java.foodplannerbykhalidamr.authentication.emailAuth.EmailAuthPresenterInterface;

public class EmailSignupFragment extends Fragment implements EmailAuthViewer {
   // private EmailAuthPresenterInterface presenter;
    private EmailAuthPresenter presenter;
    private FavoriteDao favoriteDao ;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_email_signup, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new EmailAuthPresenter(new EmailAuth(), this,new FirestoreSyncHelper(favoriteDao));
        favoriteDao = AppDatabase.getInstance(requireContext()).favoriteDao();

        TextInputLayout emailLayout = view.findViewById(R.id.emailInputLayout);
        TextInputLayout passwordLayout = view.findViewById(R.id.passwordInputLayout);
        TextInputLayout confirmPasswordLayout = view.findViewById(R.id.reenterPasswordLayout);
        EditText emailField = emailLayout.getEditText();
        EditText passwordField = passwordLayout.getEditText();
        EditText confirmPasswordField = confirmPasswordLayout.getEditText();
        Button signUpButton = view.findViewById(R.id.btn_signup);

        signUpButton.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();
            String confirmPassword = confirmPasswordField.getText().toString().trim();

            if (!password.equals(confirmPassword)) {
                showError("Passwords do not match");
                return;
            }

            presenter.signUp(email, password);
        });
    }

    @Override
    public void showSuccess(String message) {
        getActivity().getSharedPreferences("AppPrefs", getContext().MODE_PRIVATE)
                .edit()
                .remove("isGuest")
                .apply();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), "Signup Failed: " + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToHome() {
        Navigation.findNavController(requireView()).navigate(R.id.action_emailSignupFragment_to_homeFragment);
    }
}
