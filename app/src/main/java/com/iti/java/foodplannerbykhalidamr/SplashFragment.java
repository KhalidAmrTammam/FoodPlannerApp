package com.iti.java.foodplannerbykhalidamr;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.view.LayoutInflater;
import android.view.ViewGroup;


public class SplashFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        new Handler().postDelayed(() -> {
            NavController navController = NavHostFragment.findNavController(SplashFragment.this);

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (navController.getCurrentDestination() != null
                    && navController.getCurrentDestination().getId() == R.id.splashFragment4) {

                if (user != null) {
                    navController.navigate(R.id.action_splashFragment4_to_homeFragment);
                } else {
                    navController.navigate(R.id.action_splashFragment4_to_authenticationFragment42);
                }
            }
        }, 3000); // Splash screen delay
    }
}
