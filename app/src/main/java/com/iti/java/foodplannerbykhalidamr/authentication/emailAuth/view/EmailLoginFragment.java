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
import com.iti.java.foodplannerbykhalidamr.favorites.model.AppDatabase;
import com.iti.java.foodplannerbykhalidamr.favorites.model.FavoriteDao;
import com.iti.java.foodplannerbykhalidamr.favorites.model.FirestoreSyncHelper;
//import com.iti.java.foodplannerbykhalidamr.authentication.emailAuth.EmailAuthPresenterInterface;

public class EmailLoginFragment extends Fragment implements EmailAuthViewer {
    //private EmailAuthPresenterInterface presenter;
    private EmailAuthPresenter presenter;
    private FavoriteDao favoriteDao ;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_email_login, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new EmailAuthPresenter(new EmailAuth(), this, new FirestoreSyncHelper(favoriteDao));
        favoriteDao = AppDatabase.getInstance(requireContext()).favoriteDao();

        TextInputLayout emailLayout = view.findViewById(R.id.emailInputLayout);
        TextInputLayout passwordLayout = view.findViewById(R.id.passwordInputLayout);
        EditText emailField = emailLayout.getEditText();
        EditText passwordField = passwordLayout.getEditText();
        Button loginButton = view.findViewById(R.id.btn_login);

        loginButton.setOnClickListener(v ->
                presenter.login(emailField.getText().toString().trim(), passwordField.getText().toString().trim())

        );
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
        Toast.makeText(getActivity(), "Login Failed: " + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToHome() {
        Navigation.findNavController(requireView()).navigate(R.id.action_emailLoginFragment2_to_homeFragment);
    }
}
