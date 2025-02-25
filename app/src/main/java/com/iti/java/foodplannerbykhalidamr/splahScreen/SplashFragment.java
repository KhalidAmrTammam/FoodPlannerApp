package com.iti.java.foodplannerbykhalidamr.splahScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.iti.java.foodplannerbykhalidamr.R;

public class SplashFragment extends Fragment implements SplashViewer {
    private SplashPresenter presenter;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        presenter = new SplashPresenter(this);
        presenter.checkUserSession();
    }

    @Override
    public void navigateToHome() {
        if (navController.getCurrentDestination() != null
                && navController.getCurrentDestination().getId() == R.id.splashFragment4) {
            navController.navigate(R.id.action_splashFragment4_to_homeFragment);
        }
    }

    @Override
    public void navigateToAuthentication() {
        if (navController.getCurrentDestination() != null
                && navController.getCurrentDestination().getId() == R.id.splashFragment4) {
            navController.navigate(R.id.action_splashFragment4_to_authenticationFragment4);
        }
    }
}
