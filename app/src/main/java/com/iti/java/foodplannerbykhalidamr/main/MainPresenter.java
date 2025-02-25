package com.iti.java.foodplannerbykhalidamr.main;

import androidx.navigation.NavController;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainPresenter {
    private final MainView mainView;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }

    public void handleNavigation(NavController navController, BottomNavigationView bottomNavigationView) {
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == com.iti.java.foodplannerbykhalidamr.R.id.splashFragment4
                    || destination.getId() == com.iti.java.foodplannerbykhalidamr.R.id.authenticationFragment4
                    || destination.getId() == com.iti.java.foodplannerbykhalidamr.R.id.emailLoginFragment2
                    || destination.getId() == com.iti.java.foodplannerbykhalidamr.R.id.emailSignupFragment) {
                mainView.hideBottomNav();
            } else {
                mainView.showBottomNav();
            }
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            mainView.animateNavItem(bottomNavigationView.findViewById(item.getItemId()));
            return androidx.navigation.ui.NavigationUI.onNavDestinationSelected(item, navController);
        });
    }
}
